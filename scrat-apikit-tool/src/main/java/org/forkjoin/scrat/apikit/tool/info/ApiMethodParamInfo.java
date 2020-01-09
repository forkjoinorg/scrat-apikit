package org.forkjoin.scrat.apikit.tool.info;

import org.apache.commons.lang3.StringUtils;
import org.forkjoin.scrat.apikit.type.ApiMethodParamType;

import static org.forkjoin.scrat.apikit.type.ApiMethodParamType.*;

/**
 * @author zuoge85 on 15/12/9.
 */
public class ApiMethodParamInfo extends FieldInfo {
    private ApiMethodParamType apiMethodParamType;
    private boolean isRequired = true;
    private String annotationName;
    private String defaultValue;

    public ApiMethodParamInfo(String name, TypeInfo typeInfo) {
        super(name, typeInfo);
    }



    public boolean isFormParam() {
        return apiMethodParamType.equals(FORM_PARAM);
    }

    public boolean isPathVariable() {
        return apiMethodParamType.equals(PATH_VARIABLE);
    }

    public String getAnnotationName() {
        if (StringUtils.isEmpty(annotationName)) {
            return this.getName();
        }
        return annotationName;
    }

    public void setAnnotationName(String annotationName) {
        this.annotationName = annotationName;
    }

    public boolean isRequestHeader() {
        return apiMethodParamType.equals(REQUEST_HEADER);
    }

    public boolean isRequestParam() {
        return apiMethodParamType.equals(REQUEST_PARAM);
    }


    public boolean isRequestPartFile() {
        return apiMethodParamType.equals(REQUEST_PART_FILE);
    }

    public boolean isRequestPartField() {
        return apiMethodParamType.equals(REQUEST_PART_FIELD);
    }


    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public ApiMethodParamType getApiMethodParamType() {
        return apiMethodParamType;
    }

    public void setApiMethodParamType(ApiMethodParamType apiMethodParamType) {
        this.apiMethodParamType = apiMethodParamType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return "ApiMethodParamInfo{" +
                "apiMethodParamType=" + apiMethodParamType +
                ", isRequired=" + isRequired +
                ", annotationName='" + annotationName + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                "} " + super.toString();
    }
}
