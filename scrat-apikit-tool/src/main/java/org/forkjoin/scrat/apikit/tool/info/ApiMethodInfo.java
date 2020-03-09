package org.forkjoin.scrat.apikit.tool.info;


import org.apache.commons.collections4.CollectionUtils;
import org.forkjoin.scrat.apikit.core.ActionType;
import org.forkjoin.scrat.apikit.tool.utils.NameUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zuoge85 on 15/6/12.
 */
public class ApiMethodInfo {
    private int index;
    private String name;
    private String url;
    private ActionType[] types = new ActionType[]{ActionType.GET};
    private boolean account = true;
    private boolean isFlux = false;
    private boolean isMono = false;


    //    private boolean isPathVariable = false;
//    private boolean isFormParam = false;
    private ArrayList<ApiMethodParamInfo> params = new ArrayList<>();
    private ArrayList<ApiMethodParamInfo> pathParams = new ArrayList<>();
    private ArrayList<ApiMethodParamInfo> formParams = new ArrayList<>();
    private ArrayList<ApiMethodParamInfo> requestPartFieldParams = new ArrayList<>();
    private ArrayList<ApiMethodParamInfo> requestPartFileParams = new ArrayList<>();


    /**
     * 原始类型
     */
    private TypeInfo resultType;
    /**
     * 使用result 包装的类型
     */
    private TypeInfo resultWrappedType;
    /**
     * 返回值的类型
     */
    private TypeInfo resultDataType;
    private JavadocInfo comment;

    private List<AnnotationInfo> annotations = new ArrayList<>();
    private String file;

    public ApiMethodInfo() {
    }

    public void addParam(ApiMethodParamInfo param) {
        params.add(param);
        if (param.isPathVariable()) {
            pathParams.add(param);
        }
        if (param.isFormParam()) {
            formParams.add(param);
        }
        if (param.isRequestPartField()) {
            requestPartFieldParams.add(param);
        }
        if (param.isRequestPartFile()) {
            requestPartFileParams.add(param);
        }
    }

    public boolean isHasForm() {
        return formParams.size() > 0;
    }

    public boolean isHasPart() {
        return requestPartFieldParams.size() > 0 || requestPartFileParams.size() > 0;
    }

    protected void findTypes(TypeInfo type, List<TypeInfo> list) {
        list.add(type);
        if (CollectionUtils.isNotEmpty(type.getTypeArguments())) {
            type.getTypeArguments().forEach(t -> findTypes(t, list));
        }
    }

    public List<TypeInfo> getAllTypes() {
        return Stream.of(this)
                .flatMap(m -> {
                    List<TypeInfo> types = new ArrayList<>();
                    types.add(m.getResultDataType());
                    m.getParams().forEach(p -> types.add(p.getTypeInfo()));
                    return types.stream();
                })
                .flatMap(type -> {
                    List<TypeInfo> types = new ArrayList<>();
                    findTypes(type, types);
                    return types.stream();
                })
                .filter(typeInfo -> typeInfo.getType().equals(TypeInfo.Type.OTHER))
                .filter(typeInfo -> !typeInfo.isCollection())
                .filter(typeInfo -> !typeInfo.isGeneric())
                .distinct()
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }
    public String getUpperName() {
        return NameUtils.toUpperName(this.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ActionType getType() {
        return types.length > 0 ? types[0] : null;
    }

    public ActionType[] getTypes() {
        return types;
    }

    public void setTypes(ActionType[] types) {
        this.types = types;
    }

    public TypeInfo getResultType() {
        return resultType;
    }

    public void setResultType(TypeInfo resultType) {
        this.resultType = resultType;
    }

    public void addAnnotation(AnnotationInfo annotation) {
        annotations.add(annotation);
    }

    public List<AnnotationInfo> getAnnotations() {
        return annotations;
    }

    public boolean isAccount() {
        return account;
    }

    public void setAccount(boolean account) {
        this.account = account;
    }

    public void setComment(JavadocInfo comment) {
        this.comment = comment;
    }

    public JavadocInfo getComment() {
        return comment;
    }

    public ArrayList<ApiMethodParamInfo> getParams() {
        return params;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public ArrayList<ApiMethodParamInfo> getFormParams() {
        return formParams;
    }

    public ArrayList<ApiMethodParamInfo> getPathParams() {
        return pathParams;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public TypeInfo getResultWrappedType() {
        return resultWrappedType;
    }

    public void setResultWrappedType(TypeInfo resultWrappedType) {
        this.resultWrappedType = resultWrappedType;
    }

    public TypeInfo getResultDataType() {
        return resultDataType;
    }

    public void setResultDataType(TypeInfo resultDataType) {
        this.resultDataType = resultDataType;
    }

    public boolean isFlux() {
        return isFlux;
    }

    public void setFlux(boolean flux) {
        isFlux = flux;
    }

    public boolean isMono() {
        return isMono;
    }

    public void setMono(boolean mono) {
        isMono = mono;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ApiMethodInfo.class.getSimpleName() + "[", "]")
                .add("index=" + index)
                .add("name='" + name + "'")
                .add("url='" + url + "'")
                .add("types=" + Arrays.toString(types))
                .add("account=" + account)
                .add("isFlux=" + isFlux)
                .add("isMono=" + isMono)
                .add("params=" + params)
                .add("pathParams=" + pathParams)
                .add("formParams=" + formParams)
                .add("requestPartFieldParams=" + requestPartFieldParams)
                .add("requestPartFileParams=" + requestPartFileParams)
                .add("resultType=" + resultType)
                .add("resultWrappedType=" + resultWrappedType)
                .add("resultDataType=" + resultDataType)
                .add("comment=" + comment)
                .add("annotations=" + annotations)
                .add("file='" + file + "'")
                .toString();
    }
}
