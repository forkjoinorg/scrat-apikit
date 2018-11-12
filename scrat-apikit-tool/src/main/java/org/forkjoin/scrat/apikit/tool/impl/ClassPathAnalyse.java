package org.forkjoin.scrat.apikit.tool.impl;

import com.thoughtworks.paranamer.AnnotationParanamer;
import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.forkjoin.scrat.apikit.core.Account;
import org.forkjoin.scrat.apikit.core.ActionType;
import org.forkjoin.scrat.apikit.core.Ignore;
import org.forkjoin.scrat.apikit.tool.Analyse;
import org.forkjoin.scrat.apikit.tool.AnalyseException;
import org.forkjoin.scrat.apikit.tool.Context;
import org.forkjoin.scrat.apikit.tool.info.ApiInfo;
import org.forkjoin.scrat.apikit.tool.info.ApiMethodInfo;
import org.forkjoin.scrat.apikit.tool.info.ApiMethodParamInfo;
import org.forkjoin.scrat.apikit.tool.info.TypeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

public class ClassPathAnalyse implements Analyse {
    private static final Logger log = LoggerFactory.getLogger(ClassPathAnalyse.class);

    private Context context;

    @Override
    public void analyse(Context context) {
        this.context = context;

        FastClasspathScanner fastClasspathScanner = new FastClasspathScanner(context.getRootPackage());
        fastClasspathScanner.addClassLoader(ClassPathAnalyse.class.getClassLoader());
        fastClasspathScanner.matchAllClasses(this::analyse);
        ScanResult scanResult = fastClasspathScanner.scan();
    }

    private void analyse(Class cls) {
        try {
            Ignore ignoreAnnotation = AnnotationUtils.getAnnotation(cls, Ignore.class);
            Controller controllerAnnotation = AnnotationUtils.getAnnotation(cls, Controller.class);
            RequestMapping requestMappingAnnotation = AnnotationUtils.getAnnotation(cls, RequestMapping.class);


            String path = (requestMappingAnnotation != null && ArrayUtils.isNotEmpty(requestMappingAnnotation.path()))
                    ? requestMappingAnnotation.path()[0]
                    : "";

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

        methodInfo.setComment(jdtClassWapper.getMethodComment(method.getName()));
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


            Type pType = parameter.getParameterizedType();
            ApiMethodParamInfo fieldInfo = new ApiMethodParamInfo(paramName, TypeInfo.form(pType));

            AnnotationAttributes pathVarAnnotationAttributes = AnnotatedElementUtils.getMergedAnnotationAttributes(parameter, PathVariable.class);
            if (MapUtils.isNotEmpty(pathVarAnnotationAttributes)) {
                fieldInfo.setPathVariable(true);
            }

            Valid validAnnotation = AnnotationUtils.getAnnotation(parameter, Valid.class);
            if (validAnnotation != null) {
                fieldInfo.setFormParam(true);
            }
            if (!fieldInfo.isFormParam() && !fieldInfo.isPathVariable()) {
                continue;
            }

            if (fieldInfo.isFormParam() && fieldInfo.isPathVariable()) {
                throw new AnalyseException("参数不能同时是路径参数和form" + fieldInfo);
            }
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


    private void analyseReturnInfo(Type type, ApiMethodInfo apiMethodInfo) {
        if (type == null) {
            throw new AnalyseException("返回类型不能为空!" + apiMethodInfo);
        }
        TypeInfo resultType = TypeInfo.form(type);
        TypeInfo.form(type);
        /*
         * 真真正正的返回类型
         */
        Class<?> cls = null;
        try {
            cls = resultType.toClass();
        } catch (ClassNotFoundException ignored) {
        }
        if (cls != null
                && (Flux.class.isAssignableFrom(cls)
                || Mono.class.isAssignableFrom(cls))) {
            if (CollectionUtils.isEmpty(resultType.getTypeArguments())) {
                throw new AnalyseException("类型不存在！!" + resultType);
            }
            if (resultType.getTypeArguments().size() != 1) {
                throw new AnalyseException("返回参数的类型变量数只能是1！!" + resultType);
            }
            boolean isSingle = Mono.class.isAssignableFrom(cls);
            TypeInfo realResultType = resultType.getTypeArguments().get(0);

//            TypeInfo resultWrappedType = newResultTypeInfo(realResultType);
            if (isSingle) {
                apiMethodInfo.setResultType(realResultType);
//                apiMethodInfo.setResultWrappedType(resultWrappedType);

                apiMethodInfo.setResultDataType(realResultType);
            } else {
                TypeInfo realResultTypeArray = realResultType.clone();
                realResultTypeArray.setArray(true);

                apiMethodInfo.setResultType(realResultTypeArray);
//                apiMethodInfo.setResultWrappedType(resultWrappedType);


                apiMethodInfo.setResultDataType(realResultTypeArray);
            }
        } else {
            apiMethodInfo.setResultType(resultType);
//            TypeInfo resultWrappedType = newResultTypeInfo(resultType);
//
//            apiMethodInfo.setResultWrappedType(resultWrappedType);
            apiMethodInfo.setResultDataType(resultType);
        }
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
