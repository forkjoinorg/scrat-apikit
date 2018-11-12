package org.forkjoin.scrat.apikit.tool.wrapper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.forkjoin.scrat.apikit.tool.Context;
import org.forkjoin.scrat.apikit.tool.generator.NameMaper;
import org.forkjoin.scrat.apikit.tool.info.ApiInfo;
import org.forkjoin.scrat.apikit.tool.info.ApiMethodInfo;
import org.forkjoin.scrat.apikit.tool.info.ApiMethodParamInfo;
import org.forkjoin.scrat.apikit.tool.info.TypeInfo;
import org.forkjoin.scrat.apikit.tool.utils.CommentUtils;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zuoge85 on 15/6/14.
 */
public class JavaApiWrapper extends JavaWrapper<ApiInfo> {
    private String version;
    private NameMaper apiNameMaper;

    public JavaApiWrapper(Context context, ApiInfo moduleInfo, String rootPackage, NameMaper apiNameMaper) {
        super(context, moduleInfo, rootPackage);
        this.apiNameMaper = apiNameMaper;
    }

    @Override
    public String getName() {
        return apiNameMaper.apply(super.getName());
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
        StringBuilder sb = new StringBuilder(start);
        sb.append("<div class='http-info'>http 说明")
                .append("<ul>\n");

        sb.append(start).append("<li><b>Uri:</b>").append(method.getUrl()).append("</li>\n");

        Map<String, String> stringStringMap = CommentUtils.commentToMap(method.getComment());

        ArrayList<ApiMethodParamInfo> params = method.getParams();
        for (ApiMethodParamInfo attributeInfo : params) {
            if (attributeInfo.isPathVariable()) {
                String name = attributeInfo.getName();
                String txt = stringStringMap.get(name);
                sb.append(start).append("<li><b>PathVariable:</b> ")
                        .append(
                                StringEscapeUtils.escapeHtml4(
                                        toJavaTypeString(attributeInfo.getTypeInfo(), false, true)
                                )
                        )
                        .append(" ")
                        .append(attributeInfo.getName());

                if (StringUtils.isNotEmpty(txt)) {
                    sb.append(" ");
                    sb.append("<span>");
                    sb.append(txt);
                    sb.append("</span>");
                }
                sb.append("</li>\n");
            } else {
                sb.append(start).append("<li><b>Form:</b>")
                        .append(
                                StringEscapeUtils.escapeHtml4(
                                        toJavaTypeString(attributeInfo.getTypeInfo(), false, true)
                                )
                        )
                        .append("")
                        .append(method.getName())
                        .append("</li>\n");
            }
        }

        String returnType = toJavaTypeString(method.getResultType(), false, true);

        sb.append(start).append("<li><b>Model:</b> ").append("").append(
                StringEscapeUtils.escapeHtml4(returnType)
        ).append("").append("</li>\n");

        if (method.isAccount()) {
            sb.append(start).append("<li>需要登录</li>\n");
        }


        sb.append(start).append("</ul>\n").append(start).append("</div>\n");

        Map<String, ApiMethodParamInfo> paramMap = method
                .getParams()
                .stream()
                .collect(Collectors.toMap(ApiMethodParamInfo::getName, r -> r));

        List<List<String>> param = method.getComment().get("@param");
        if(CollectionUtils.isNotEmpty(param)){
            param.stream().filter(r->r.size()>1).forEach(list->{
                ApiMethodParamInfo methodParamInfo = paramMap.get(list.get(0));
                if(methodParamInfo != null){
                    sb.append(start).append("@param ")
                            .append(list.get(0))
                            .append(" ")
                            .append(list.size()>1? String.join(" ", list.subList(1, list.size())) :"")
                            .append("\n");
                }
            });
        }

        method.getAllTypes().forEach(typeInfo -> {
            sb.append(start).append("@see ").append(
                    StringEscapeUtils.escapeHtml4(
                            toJavaTypeString(typeInfo, false, true, false)
                    )
            ).append("\n");
        });
        return StringUtils.stripEnd(sb.toString(), null);
    }


    public String params(ApiMethodInfo method) {
        return params(method, true);
    }

    public String params(ApiMethodInfo method, boolean isAnnotation) {
        StringBuilder sb = new StringBuilder();
        ArrayList<ApiMethodParamInfo> params = method.getParams();
        for (int i = 0; i < params.size(); i++) {
            ApiMethodParamInfo attributeInfo = params.get(i);
            if (i > 0) {
                sb.append(", ");
            }
            if (isAnnotation) {
                if (attributeInfo.isPathVariable()) {
                    sb.append("@").append(PathVariable.class.getSimpleName()).append(" ");
                }
                if (attributeInfo.isFormParam()) {
                    sb.append("@").append(Valid.class.getSimpleName()).append(" ");
                }
            }
            sb.append(toJavaTypeString(attributeInfo.getTypeInfo(), false, true));
            sb.append(' ');
            sb.append(attributeInfo.getName());
        }
        return sb.toString();
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
