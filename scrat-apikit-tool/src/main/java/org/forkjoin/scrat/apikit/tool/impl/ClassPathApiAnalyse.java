package org.forkjoin.scrat.apikit.tool.impl;

import com.thoughtworks.paranamer.AnnotationParanamer;
import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.forkjoin.scrat.apikit.core.Account;
import org.forkjoin.scrat.apikit.core.ActionType;
import org.forkjoin.scrat.apikit.core.Ignore;
import org.forkjoin.scrat.apikit.tool.AnalyseException;
import org.forkjoin.scrat.apikit.tool.ApiAnalyse;
import org.forkjoin.scrat.apikit.tool.Context;
import org.forkjoin.scrat.apikit.tool.info.ApiInfo;
import org.forkjoin.scrat.apikit.tool.info.ApiMethodInfo;
import org.forkjoin.scrat.apikit.tool.info.ApiMethodParamInfo;
import org.forkjoin.scrat.apikit.tool.info.TypeInfo;
import org.forkjoin.scrat.apikit.type.ApiMethodParamType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassPathApiAnalyse implements ApiAnalyse {
    private static final Logger log = LoggerFactory.getLogger(ClassPathApiAnalyse.class);

    private Context context;

    @Override
    public void analyse(Context context) {
        this.context = context;

        FastClasspathScanner fastClasspathScanner = new FastClasspathScanner(context.getRootPackage());
        fastClasspathScanner.addClassLoader(ClassPathApiAnalyse.class.getClassLoader());
        fastClasspathScanner.matchAllClasses(this::analyse);
        ScanResult scanResult = fastClasspathScanner.scan();
    }

    private void analyse(Class cls) {
        try {
            Ignore ignoreAnnotation = AnnotationUtils.getAnnotation(cls, Ignore.class);
            Controller controllerAnnotation = AnnotationUtils.getAnnotation(cls, Controller.class);
            RequestMapping requestMappingAnnotation = AnnotationUtils.getAnnotation(cls, RequestMapping.class);


            String path;
            if (requestMappingAnnotation != null && ArrayUtils.isNotEmpty(requestMappingAnnotation.path())) {
                path = requestMappingAnnotation.path()[0];
            } else {
                path = "";
            }

            String name = cls.getPackage().getName();


            if (ignoreAnnotation == null &&
                    controllerAnnotation != null &&
                    name.startsWith(context.getRootPackage())) {


                JdtClassWappper jdtClassWapper = new JdtClassWappper(context.getPath(), cls);

                ApiInfo apiInfo = new ApiInfo();
                apiInfo.setName(cls.getSimpleName());
                apiInfo.setPackageName(name);
                apiInfo.setComment(jdtClassWapper.getClassComment());

                List<ApiMethodInfo> list = Flux
                        .just(cls.getMethods())
                        .filter(m ->
                                AnnotationUtils.getAnnotation(m, RequestMapping.class) != null
                                        && AnnotationUtils.getAnnotation(m, Ignore.class) == null)
                        .map(method -> this.analyseMethod(method, path, jdtClassWapper))
                        .sort((m1, m2) -> StringUtils.compare(m1.getName(), m2.getName()))
                        .collectList()
                        .block();


                Objects.requireNonNull(list).forEach(apiInfo::addApiMethod);

                context.addApi(apiInfo);
            }
        } catch (Throwable th) {
            log.info("分析错误!class:{}", cls, th);
            throw new RuntimeException(th);
        }
    }

    private ApiMethodInfo analyseMethod(Method method, String parentPath, JdtClassWappper jdtClassWapper) {
        AnnotationAttributes annotationAttributes = AnnotatedElementUtils.getMergedAnnotationAttributes(method, RequestMapping.class);
        String[] pathArray = annotationAttributes.getStringArray("path");
        RequestMethod[] requestMethods = (RequestMethod[]) annotationAttributes.get("method");

        ApiMethodInfo methodInfo = new ApiMethodInfo();

        methodInfo.setComment(jdtClassWapper.getMethodComment(method));
        methodInfo.setTypes(toActionTypes(requestMethods));
        methodInfo.setName(method.getName());

        String curPath = toPath(parentPath, pathArray);
        methodInfo.setUrl(curPath);

        Account account = AnnotationUtils.getAnnotation(method, Account.class);
        methodInfo.setAccount(account == null || account.value());


        Type type = method.getGenericReturnType();


        analyseReturnInfo(type, methodInfo);

        analyseMethodParamsInfo(methodInfo, method);

        return methodInfo;
    }


    /**
     * 处理函数参数
     */
    private void analyseMethodParamsInfo(ApiMethodInfo apiMethodInfo, Method method) {
        Parameter[] parameters = method.getParameters();
        Paranamer info = new CachingParanamer(new AnnotationParanamer(new BytecodeReadingParanamer()));
        String[] parameterNames = info.lookupParameterNames(method);
        for (int i = 0, parametersLength = parameters.length; i < parametersLength; i++) {
            Parameter parameter = parameters[i];
            if (parameter.isVarArgs()) {
                throw new AnalyseException("暂时不支持可变参数 varargs");
            }
            String paramName = parameterNames[i];
            if (paramName == null) {
                throw new AnalyseException("请修改编译选项，保留方法参数名称");
            }

            Ignore annotation = AnnotationUtils.getAnnotation(parameter, Ignore.class);
            if (annotation != null) {
                continue;
            }


            Type pType = parameter.getParameterizedType();
            TypeInfo typeInfo = TypeInfo.form(pType);
            ApiMethodParamInfo fieldInfo;

            if (isGenericWrapper(typeInfo)) {
                if (CollectionUtils.isEmpty(typeInfo.getTypeArguments())) {
                    throw new AnalyseException("类型不存在！!" + typeInfo);
                }
                if (typeInfo.getTypeArguments().size() != 1) {
                    throw new AnalyseException("返回参数的类型变量数只能是1！!" + typeInfo);
                }
                boolean isSingle = isMono(typeInfo);
                TypeInfo realResultType = typeInfo.getTypeArguments().get(0);

                if (isSingle) {
                    fieldInfo = new ApiMethodParamInfo(paramName, realResultType);
                } else {
                    TypeInfo realResultTypeArray = realResultType.clone();
                    realResultTypeArray.setArray(true);

                    apiMethodInfo.setResultType(realResultTypeArray);
//                  apiMethodInfo.setResultWrappedType(resultWrappedType);
                    apiMethodInfo.setResultDataType(realResultTypeArray);

                    fieldInfo = new ApiMethodParamInfo(paramName, realResultTypeArray);
                }
            } else {
                fieldInfo = new ApiMethodParamInfo(paramName, typeInfo);
            }


            ApiMethodParamType apiMethodParamType;
            if (analyse(fieldInfo, AnnotationUtils.getAnnotation(parameter, PathVariable.class))) {
                apiMethodParamType = ApiMethodParamType.PATH_VARIABLE;
            } else if (analyse(fieldInfo, AnnotationUtils.getAnnotation(parameter, Valid.class))) {
                apiMethodParamType = ApiMethodParamType.FORM_PARAM;
            } else if (analyse(fieldInfo, AnnotationUtils.getAnnotation(parameter, RequestHeader.class))) {
                apiMethodParamType = ApiMethodParamType.REQUEST_HEADER;
            } else if (analyse(fieldInfo, AnnotationUtils.getAnnotation(parameter, RequestParam.class))) {
                apiMethodParamType = ApiMethodParamType.REQUEST_PARAM;
            } else {
                apiMethodParamType = analyse(fieldInfo, AnnotationUtils.getAnnotation(parameter, RequestPart.class));
            }

            if (apiMethodParamType == null) {
                continue;
            }

            fieldInfo.setApiMethodParamType(apiMethodParamType);

            if (fieldInfo.isFormParam()) {
                if (fieldInfo.getTypeInfo().isArray()) {
                    throw new AnalyseException("表单对象不支持数组!" + fieldInfo);
                }
            }
            if (fieldInfo.getTypeInfo().getType() == TypeInfo.Type.VOID) {
                throw new AnalyseException("void 类型只能用于返回值");
            }
            apiMethodInfo.addParam(fieldInfo);
        }
    }

    private boolean analyse(ApiMethodParamInfo fieldInfo, Valid annotation) {
        if (annotation != null) {
            return true;
        }
        return false;
    }


    private boolean analyse(ApiMethodParamInfo fieldInfo, PathVariable annotation) {
        if (annotation != null) {
            fieldInfo.setRequired(annotation.required());
            fieldInfo.setAnnotationName(StringUtils.isNotEmpty(annotation.name()) ? annotation.name() : annotation.value());
            return true;
        }
        return false;
    }

    private boolean analyse(ApiMethodParamInfo fieldInfo, RequestParam annotation) {
        if (annotation != null) {
            fieldInfo.setRequired(annotation.required());
            fieldInfo.setAnnotationName(StringUtils.isNotEmpty(annotation.name()) ? annotation.name() : annotation.value());
            fieldInfo.setDefaultValue(annotation.defaultValue());
            return true;
        }
        return false;
    }

    private boolean analyse(ApiMethodParamInfo fieldInfo, RequestHeader annotation) {
        if (annotation != null) {
            fieldInfo.setRequired(annotation.required());
            fieldInfo.setAnnotationName(StringUtils.isNotEmpty(annotation.name()) ? annotation.name() : annotation.value());
            fieldInfo.setDefaultValue(annotation.defaultValue());
            return true;
        }
        return false;
    }

    private ApiMethodParamType analyse(ApiMethodParamInfo fieldInfo, RequestPart annotation) {
        if (annotation != null) {
            fieldInfo.setRequired(annotation.required());
            fieldInfo.setAnnotationName(StringUtils.isNotEmpty(annotation.name()) ? annotation.name() : annotation.value());

            TypeInfo filePartTypeInfo = fieldInfo.getTypeInfo().findByMyAndTypeArguments(
                    FilePart.class.getPackage().getName(),
                    FilePart.class.getSimpleName()
            );

            TypeInfo formFieldPartTypeInfo = fieldInfo.getTypeInfo().findByMyAndTypeArguments(
                    FormFieldPart.class.getPackage().getName(),
                    FormFieldPart.class.getSimpleName()
            );

            if (filePartTypeInfo != null) {
                return ApiMethodParamType.REQUEST_PART_FILE;
            } else if (formFieldPartTypeInfo != null) {
                return ApiMethodParamType.REQUEST_PART_FIELD;
            } else {
                throw new AnalyseException("不支持的Part" + fieldInfo);
            }
        }
        return null;
    }


    private void analyseReturnInfo(Type type, ApiMethodInfo apiMethodInfo) {
        if (type == null) {
            throw new AnalyseException("返回类型不能为空!" + apiMethodInfo);
        }
        TypeInfo resultType = TypeInfo.form(type);
        if (isGenericWrapper(resultType)) {
            if (CollectionUtils.isEmpty(resultType.getTypeArguments())) {
                throw new AnalyseException("类型不存在！!" + resultType);
            }
            if (resultType.getTypeArguments().size() != 1) {
                throw new AnalyseException("返回参数的类型变量数只能是1！!" + resultType);
            }
            TypeInfo realResultType = resultType.getTypeArguments().get(0);

            apiMethodInfo.setMono(isMono(resultType));
            apiMethodInfo.setFlux(isFlux(resultType));
//            TypeInfo resultWrappedType = newResultTypeInfo(realResultType);
            apiMethodInfo.setResultType(realResultType);
            apiMethodInfo.setResultDataType(realResultType);
        } else {
            apiMethodInfo.setResultType(resultType);
//            TypeInfo resultWrappedType = newResultTypeInfo(resultType);
//
//            apiMethodInfo.setResultWrappedType(resultWrappedType);
            apiMethodInfo.setResultDataType(resultType);
        }
    }

    private boolean isMono(TypeInfo cls) {
        return equals(cls, Mono.class);
    }

    private boolean isFlux(TypeInfo cls) {
        return equals(cls, Flux.class);
    }

    private boolean equals(TypeInfo typeInfo, Class<?> cls) {
        return Objects.equals(typeInfo.getPackageName(), cls.getPackage().getName()) &&
                Objects.equals(typeInfo.getName(), cls.getSimpleName());
    }

    private boolean isGenericWrapper(TypeInfo cls) {
        return cls != null
                && (equals(cls, Mono.class) || equals(cls, Flux.class));
    }


    private String toPath(String parentPath, String[] pathArray) {
        String path = (ArrayUtils.isNotEmpty(pathArray))
                ? pathArray[0]
                : "";

        return Stream.of(parentPath, path)
                .flatMap(p -> Stream.of(p.split("/")))
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining("/", "", ""));
    }

    private ActionType[] toActionTypes(RequestMethod[] requestMapping) {
        return Flux
                .just(requestMapping)
                .map(m -> ActionType.valueOf(m.name()))
                .collectList()
                .filter(r -> !r.isEmpty())
                .switchIfEmpty(Mono.just(Arrays.asList(ActionType.values())))
                .block()
                .toArray(new ActionType[]{});
    }

}
