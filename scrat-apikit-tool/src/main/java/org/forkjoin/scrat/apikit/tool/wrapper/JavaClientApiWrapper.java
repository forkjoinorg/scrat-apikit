package org.forkjoin.scrat.apikit.tool.wrapper;

import org.apache.commons.text.StringEscapeUtils;
import org.forkjoin.scrat.apikit.tool.Context;
import org.forkjoin.scrat.apikit.tool.generator.NameMaper;
import org.forkjoin.scrat.apikit.tool.info.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//import org.forkjoin.spring.annotation.Account;
//import org.forkjoin.spring.annotation.AccountParam;

/**
 * @author zuoge85 on 15/6/17.
 */
public class JavaClientApiWrapper extends JavaApiWrapper {

    private boolean isAnnotations = false;

    public JavaClientApiWrapper(Context context, ApiInfo moduleInfo, String rootPackage, NameMaper apiNameMaper) {
        super(context, moduleInfo, rootPackage, apiNameMaper);
    }


    @Override
    public String formatAnnotations(List<AnnotationInfo> annotations, String start) {
        return null;
    }

    @Override
    public void init() {
//        initImport();
    }

    public String getImports() {
        return Flux
                .fromIterable(moduleInfo.getMethodInfos())
                .flatMapIterable(m -> {
                    List<TypeInfo> types = new ArrayList<>();
                    types.add(m.getResultDataType());
                    m.getParams().forEach(p -> types.add(p.getTypeInfo()));
                    return types;
                })
                .flatMapIterable(type -> {
                    List<TypeInfo> types = new ArrayList<>();
                    findTypes(type, types);
                    return types;
                })
                .filter(this::filterType)
                .map(ClassInfo::new)
                .map(this::map)
                .distinct()
                .sort(Comparator.naturalOrder())
                .flatMapIterable(this::toImports)
                .collect(Collectors.joining()).block();
    }


//    @Override
//    public void initImport() {
//        ArrayList<ApiMethodInfo> methodInfos = moduleInfo.getMethodInfos();
//        for (int i = 0; i < methodInfos.size(); i++) {
//            ApiMethodInfo apiMethodInfo = methodInfos.get(i);
//            addImport(apiMethodInfo.getResultType());
//
//            importSet.add(apiMethodInfo.getResultWrappedType().getFullName());
//
//            ArrayList<ApiMethodParamInfo> params = apiMethodInfo.getParams();
//            for (int j = 0; j < params.size(); j++) {
//                ApiMethodParamInfo apiMethodParamInfo = params.get(j);
//                if (apiMethodParamInfo.isFormParam() || apiMethodParamInfo.isPathVariable()) {
//                    TypeInfo typeInfo = apiMethodParamInfo.getTypeInfo();
//                    addImport(typeInfo);
//                }
//            }
//        }
//        for (String classFullName : importSet) {
//            addImport(classFullName);
//        }
//    }
//
//    private void addImport(TypeInfo type) {
//        String classFullName = getFullName(type);
//        if (type.isInside()) {
//            importSet.add(classFullName);
//        }
//        List<TypeInfo> typeArguments = type.getTypeArguments();
//        for (int i = 0; i < typeArguments.size(); i++) {
//            TypeInfo typeInfo = typeArguments.get(i);
//            addImport(typeInfo);
//        }
//    }


    public String asyncParams(ApiMethodInfo method) {
        String params = params(method);
        if (params.length() > 0) {
            params += ", ";
        }
        return params + "Callback<" + method.getResultWrappedType().getName() + "<" + result(method) + ">," + resultData(method) + "> callable";
    }

    public String asyncReuestCallbackParams(ApiMethodInfo method) {
        String params = params(method, false);
        if (params.length() > 0) {
            params += ", ";
        }
        return params + "Callback<" + result(method) + "> callable, " +
                "RequestCallback<" + result(method) + "> requestCallback";
    }


    public String params(ApiMethodInfo method) {
        return params(method, true);
    }

    public String params(ApiMethodInfo method, boolean isAnnotation) {
        StringBuilder sb = new StringBuilder();
        ArrayList<ApiMethodParamInfo> params = method.getParams();
        for (int i = 0; i < params.size(); i++) {
            ApiMethodParamInfo paramInfo = params.get(i);
            String name = paramInfo.getAnnotationName();

            if (sb.length() > 0) {
                sb.append(", ");
            }
            if (paramInfo.isRequired()) {
//                HttpEntity<?>

                sb.append(toJavaTypeString(paramInfo.getTypeInfo(), false, true));

                sb.append(' ');
                sb.append(paramInfo.getName());
            } else {
                //Optional<String> paramOpt
                sb.append("Optional<");
                sb.append(toJavaTypeString(paramInfo.getTypeInfo(), false, true));
                sb.append("> ");
                sb.append(paramInfo.getName());
                sb.append("Opt");
            }
        }
        return sb.toString();
    }

    public String toValue(ApiMethodParamInfo paramInfo) {
        TypeInfo typeInfo = paramInfo.getTypeInfo();
        String escapeJava = StringEscapeUtils.escapeJava(paramInfo.getDefaultValue());
        if (paramInfo.getDefaultValue() == null) {
            return "null";
        }

        TypeInfo.Type type = typeInfo.getType();

        switch (type) {
            case BYTE:
            case SHORT:
            case INT:
            case LONG:
            case DOUBLE:
            case FLOAT:
            case BOOLEAN: {
                return type.getName() + ".valueOf(\"" + escapeJava + "\")";
            }
            case STRING: {
                return "\"" + escapeJava + "\"";
            }
            case DATE: {
                // <T> T parseDate(String str,Class<T> cls) throws IOException;
                return "apiAdapter.parseDate(\"" + escapeJava + "\", Date.class)";
            }
            default: {
                throw new RuntimeException("不支持的类型" + typeInfo);
            }
        }
    }

    public String args(ApiMethodInfo method, boolean isAnnotation) {
        StringBuilder sb = new StringBuilder();

        ArrayList<ApiMethodParamInfo> params = method.getParams();
        for (int i = 0; i < params.size(); i++) {
            ApiMethodParamInfo attributeInfo = params.get(i);
            if (attributeInfo.isFormParam() || attributeInfo.isPathVariable()) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(attributeInfo.getName());
            }
        }
        return sb.toString();
    }

    public boolean isAnnotations() {
        return isAnnotations;
    }

    public void setAnnotations(boolean annotations) {
        isAnnotations = annotations;
    }

}
