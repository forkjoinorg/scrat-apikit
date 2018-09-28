package org.forkjoin.scrat.apikit.tool.wrapper;

import org.forkjoin.scrat.apikit.tool.Context;
import org.forkjoin.scrat.apikit.tool.generator.NameMaper;
import org.forkjoin.scrat.apikit.tool.info.AnnotationInfo;
import org.forkjoin.scrat.apikit.tool.info.ApiInfo;
import org.forkjoin.scrat.apikit.tool.info.ApiMethodInfo;
import org.forkjoin.scrat.apikit.tool.info.ApiMethodParamInfo;
import org.forkjoin.scrat.apikit.tool.info.TypeInfo;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        StringBuilder sb = new StringBuilder();
        Flux
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
                .filter(typeInfo -> typeInfo.getType().equals(TypeInfo.Type.OTHER))
                .filter(typeInfo -> !typeInfo.isCollection())
                .filter(typeInfo -> !typeInfo.isGeneric())
                .map(TypeInfo::getFullName)
                .distinct()
                .sort(Comparator.naturalOrder())
                .filter(fullName -> context.getMessageWrapper(fullName) != null)
                .map(fullName -> context.getMessageWrapper(fullName))
                .filter(w -> !w.getDistPackage().equals(getDistPackage()))
                .doOnNext(r -> sb.append("import ").append(r.getDistPack()).append(".").append(r.getDistName()).append(";\n"))
                .collectList()
                .block();

        return sb.toString();
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

    @Override
    public String params(ApiMethodInfo method, boolean isAnnotation) {
        StringBuilder sb = new StringBuilder();
        ArrayList<ApiMethodParamInfo> params = method.getParams();
        for (int i = 0; i < params.size(); i++) {
            ApiMethodParamInfo attributeInfo = params.get(i);
            if (attributeInfo.isFormParam() || attributeInfo.isPathVariable()) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(toJavaTypeString(attributeInfo.getTypeInfo(), false, true));
                sb.append(' ');
                sb.append(attributeInfo.getName());
            }
        }
        return sb.toString();
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
