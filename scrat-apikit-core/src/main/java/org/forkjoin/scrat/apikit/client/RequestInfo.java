package org.forkjoin.scrat.apikit.client;

import java.lang.reflect.Type;
import java.util.*;


public class RequestInfo {
    private String method;
    private String uri;
    private List<Map.Entry<String, Object>> form;
    private Map<String, Object> uriVariables;
    private List<Map.Entry<String, Object>> header;
    private RequestType requestType;
    private Type type;
    private boolean isAccount;

    public void addForm(EncodeObject encodeObject) {
        checkForm();
        encodeObject.encode("", form);
    }

    public void addFormParam(String name, Object value) {
        checkForm();
        form.add(new AbstractMap.SimpleImmutableEntry<>(name, value));
    }

    private void checkForm() {
        if (form == null) {
            form = new ArrayList<>();
        }
    }


    public void addPathVar(String name, Object value) {
        if (uriVariables == null) {
            uriVariables = new HashMap<>();
        }
        uriVariables.put(name, value);
    }


    public void addHeader(String name, Object value) {
        if (header == null) {
            header = new ArrayList<>();
        }
        header.add(new AbstractMap.SimpleImmutableEntry<>(name, value));
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<Map.Entry<String, Object>> getForm() {
        return form;
    }

    public void setForm(List<Map.Entry<String, Object>> form) {
        this.form = form;
    }

    public Map<String, Object> getUriVariables() {
        return uriVariables;
    }

    public void setUriVariables(Map<String, Object> uriVariables) {
        this.uriVariables = uriVariables;
    }

    public List<Map.Entry<String, Object>> getHeader() {
        return header;
    }

    public void setHeader(List<Map.Entry<String, Object>> header) {
        this.header = header;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public boolean isAccount() {
        return isAccount;
    }

    public void setAccount(boolean account) {
        isAccount = account;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RequestInfo{" +
                "method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                ", form=" + form +
                ", uriVariables=" + uriVariables +
                ", header=" + header +
                ", requestType=" + requestType +
                ", type=" + type +
                ", isAccount=" + isAccount +
                '}';
    }
}
