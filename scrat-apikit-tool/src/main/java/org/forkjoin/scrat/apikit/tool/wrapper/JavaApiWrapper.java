package org.forkjoin.scrat.apikit.tool.wrapper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.forkjoin.scrat.apikit.tool.Context;
import org.forkjoin.scrat.apikit.tool.generator.NameMapper;
import org.forkjoin.scrat.apikit.tool.info.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zuoge85 on 15/6/14.
 */
public class JavaApiWrapper extends JavaWrapper<ApiInfo> {
    private String version;
    private NameMapper apiNameMapper;


    public JavaApiWrapper(Context context, ApiInfo moduleInfo, String rootPackage, NameMapper apiNameMapper) {
        super(context, moduleInfo, rootPackage);
        this.apiNameMapper = apiNameMapper;

    }

    @Override
    public String getName() {
        return apiNameMapper.apply(super.getName());
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
        sb.append(toJavaTypeString(resultType, true, true));
        return sb.toString();
    }

    public String getImports() {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    public String resultData(ApiMethodInfo method) {
        StringBuilder sb = new StringBuilder();
        TypeInfo resultType = method.getResultDataType();
        sb.append(toJavaTypeString(resultType, true, true));
        return sb.toString();
    }

    public String resultClass(ApiMethodInfo method) {
        StringBuilder sb = new StringBuilder();
        TypeInfo resultType = method.getResultType();
        sb.append(toJavaTypeString(resultType, false, false, false));
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
                    toJavaTypeString(typeInfo, false, true, false)
            ).append("\n");
        });
        return StringUtils.stripEnd(sb.toString(), null);
    }


    public String args(ApiMethodInfo method, boolean isAnnotation) {
        StringBuilder sb = new StringBuilder();

        ArrayList<ApiMethodParamInfo> params = method.getParams();
        for (int i = 0; i < params.size(); i++) {
            ApiMethodParamInfo attributeInfo = params.get(i);
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(attributeInfo.getName());
        }
        return sb.toString();
    }

    public String resultTypeString(ApiMethodInfo method, String start) {
        StringBuilder sb = new StringBuilder(start);
        sb.append("private static final ApiType _").append(method.getIndex()).append("Type = ");
        resultTypeString(sb, method.getResultType());
        sb.append(";");
        return sb.toString();
    }

    // private static final ApiType _0Type = ApiUtils.type(Result.class,  ApiUtils.type(java.util.ArrayList.class));
    //Result<AppModel[]>
    private void resultTypeString(StringBuilder sb, TypeInfo resultType) {
        if (resultType.isArray()) {
            sb.append(" ApiUtils.type(java.util.ArrayList.class, ").append(toJavaTypeString(resultType, true, false, false)).append(".class");
        } else {
            sb.append(" ApiUtils.type(").append(toJavaTypeString(resultType, true, false, false)).append(".class");//
        }
        if (resultType.getTypeArguments().isEmpty()) {
            sb.append(")");
        } else {
            for (TypeInfo typeArgument : resultType.getTypeArguments()) {
                sb.append(",");
                resultTypeString(sb, typeArgument);
            }
            sb.append(")");
        }
    }

}
