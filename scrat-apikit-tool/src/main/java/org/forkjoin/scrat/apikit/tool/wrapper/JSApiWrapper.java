package org.forkjoin.scrat.apikit.tool.wrapper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.forkjoin.scrat.apikit.tool.Context;
import org.forkjoin.scrat.apikit.tool.generator.NameMapper;
import org.forkjoin.scrat.apikit.tool.info.*;
import org.forkjoin.scrat.apikit.tool.utils.NameUtils;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zuoge85 on 15/6/14.
 */
public class JSApiWrapper extends JSWrapper<ApiInfo> {
    private String version;
    private String jsPackageName;
    private NameMapper apiNameMapper;

    public JSApiWrapper(Context context, ApiInfo moduleInfo, String rootPackage, String jsPackageName, NameMapper apiNameMapper) {
        super(context, moduleInfo, rootPackage);
        this.jsPackageName = jsPackageName;
        this.apiNameMapper = apiNameMapper;
    }


    @Override
    public String getName() {
        return apiNameMapper.apply(super.getName());
    }


    public String getFieldName() {
        return NameUtils.toFieldName(getName());
    }

    public String getImports() {
        return getImports(true);
    }

    //
    public String getImports(boolean isModel) {
        //自己的目录级别
        int myLevel = getMyLevel();
        String imports = isModel ? getMethodImports() : "";
        return imports +
                "\nimport {AbstractApi, requestGroupImpl, RequestType} from 'apikit-core'\n";
    }


    private int getMyLevel() {
        String moduleInfoPackageName = getDistPackage();
        if (StringUtils.isEmpty(moduleInfoPackageName)) {
            return 0;
        } else {
            return moduleInfoPackageName.split(".").length + 1;
        }
    }

    public String getMethodImports() {
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
                .distinct()
                .sort(Comparator.naturalOrder())
                .flatMapIterable(this::toImports)
                .collect(Collectors.joining()).block();
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String result(ApiMethodInfo method) {
        StringBuilder sb = new StringBuilder();
        TypeInfo resultType = method.getResultType();
        sb.append(toTypeString(resultType));
        return sb.toString();
    }

    public String resultClass(ApiMethodInfo method) {
        StringBuilder sb = new StringBuilder();
        TypeInfo resultType = method.getResultType();
        sb.append(toTypeString(resultType));
        sb.append(".class");
        return sb.toString();
    }

    /**
     *
     */
    public String requestComment(ApiMethodInfo method, String start) {
        StringBuilder sb = new StringBuilder();

        Map<String, ApiMethodParamInfo> paramMap = method
                .getParams()
                .stream()
                .collect(Collectors.toMap(ApiMethodParamInfo::getName, r -> r));

        if (method.getComment() != null) {
            List<List<String>> param = method.getComment().get("@param");
            if (CollectionUtils.isNotEmpty(param)) {
                param.stream().filter(r -> r.size() > 1).forEach(list -> {
                    ApiMethodParamInfo methodParamInfo = paramMap.get(list.get(0));
                    if (methodParamInfo != null) {
                        sb.append(start).append("@param ")
                                .append(list.get(0))
                                .append(" ")
                                .append(list.size() > 1 ? String.join(" ", list.subList(1, list.size())) : "")
                                .append("\n");
                    }
                });
            }
        }

        method.getAllTypes().forEach(typeInfo -> {
            sb.append(start).append("@see ").append(
                    toTypeString(typeInfo, false)
            ).append("\n");
        });
        return StringUtils.stripEnd(sb.toString(), null);
    }

    public String params(ApiMethodInfo method) {
        return params(method, false);
    }

    public String params(ApiMethodInfo method, boolean isType) {
        StringBuilder sb = new StringBuilder();
        ArrayList<ApiMethodParamInfo> params = method.getParams();
        if (CollectionUtils.isNotEmpty(params)) {
            sb.append('{');
            for (int i = 0; i < params.size(); i++) {
                ApiMethodParamInfo paramInfo = params.get(i);

                if (i > 0) {
                    sb.append(", ");
                }
                if (paramInfo.isRequired()) {
                    sb.append(paramInfo.getName());
//                    sb.append(": ");
//                    sb.append(toTypeString(paramInfo.getTypeInfo(), true));
                } else {
                    //Optional<String> paramOpt
                    sb.append(paramInfo.getName());
                    sb.append("= ");
                    sb.append(toValue(paramInfo));
//                    sb.append(toTypeString(paramInfo.getTypeInfo(), true));
                }
            }
            sb.append("}: " + method.getUpperName() + "Args");
        }
        return sb.toString();
    }

    public String getJsPackageName() {
        return jsPackageName;
    }

    public void setJsPackageName(String jsPackageName) {
        this.jsPackageName = jsPackageName;
    }

    public String resultTypeString(ApiMethodInfo method) {
        String returnType = toTypeString(method.getResultType());
        return returnType;
    }

    public String toValue(ApiMethodParamInfo paramInfo) {
        TypeInfo typeInfo = paramInfo.getTypeInfo();
        String escapeJava = StringEscapeUtils.escapeEcmaScript(paramInfo.getDefaultValue());
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
                return escapeJava;
            }
            case STRING: {
                return "\"" + escapeJava + "\"";
            }
            case DATE: {
                // <T> T parseDate(String str,Class<T> cls) throws IOException;
                return "super._parseDate(\"" + escapeJava + "\")";
            }
            default: {
                throw new RuntimeException("不支持的类型" + typeInfo);
            }
        }
    }
//
//    private void resultTypeString(StringBuilder sb, TypeInfo resultType) {
//        if (resultType.getTypeArguments().isEmpty()) {
//            sb.append(" type(").append(resultType.getNewJavaTypeString(true, false, false)).append(".class)");
//        } else {
//            sb.append(" type(").append(resultType.getNewJavaTypeString(true, false, false)).append(".class");//
//            for (TypeInfo typeArgument : resultType.getTypeArguments()) {
//                sb.append(",");
//                resultTypeString(sb, typeArgument);
//            }
//            sb.append(")");
//        }
//    }
}
