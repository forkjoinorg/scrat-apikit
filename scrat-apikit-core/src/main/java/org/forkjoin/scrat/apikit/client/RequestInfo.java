package org.forkjoin.scrat.apikit.client;

import java.lang.reflect.Type;
import java.util.*;


public class RequestInfo {
    private String method;
    private String uri;
    private List<Map.Entry<String, Object>> form;
    private List<Map.Entry<String, Object>> requestParams;
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

    public <T> void addFormParam(String name, Collection<T> value) {
        checkForm();
        if (value != null && value.size() > 0) {
            for (T t : value) {
                form.add(new AbstractMap.SimpleImmutableEntry<>(name, t));
            }
        }
    }

    public <T> void addFormParam(String name, T[] value) {
        checkForm();
        if (value != null && value.length > 0) {
            for (T t : value) {
                form.add(new AbstractMap.SimpleImmutableEntry<>(name, t));
            }
        }
    }

    public void addFormParam(String name, byte[] value) {
        checkForm();
        if (value != null && value.length > 0) {
            for (byte t : value) {
                form.add(new AbstractMap.SimpleImmutableEntry<>(name, t));
            }
        }
    }

    public void addFormParam(String name, short[] value) {
        checkForm();
        if (value != null && value.length > 0) {
            for (short t : value) {
                form.add(new AbstractMap.SimpleImmutableEntry<>(name, t));
            }
        }
    }

    public void addFormParam(String name, int[] value) {
        checkForm();
        if (value != null && value.length > 0) {
            for (int t : value) {
                form.add(new AbstractMap.SimpleImmutableEntry<>(name, t));
            }
        }
    }

    public void addFormParam(String name, long[] value) {
        checkForm();
        if (value != null && value.length > 0) {
            for (long t : value) {
                form.add(new AbstractMap.SimpleImmutableEntry<>(name, t));
            }
        }
    }

    public void addFormParam(String name, float[] value) {
        checkForm();
        if (value != null && value.length > 0) {
            for (float t : value) {
                form.add(new AbstractMap.SimpleImmutableEntry<>(name, t));
            }
        }
    }

    public void addFormParam(String name, double[] value) {
        checkForm();
        if (value != null && value.length > 0) {
            for (double t : value) {
                form.add(new AbstractMap.SimpleImmutableEntry<>(name, t));
            }
        }
    }

    public void addFormParam(String name, boolean[] value) {
        checkForm();
        if (value != null && value.length > 0) {
            for (boolean t : value) {
                form.add(new AbstractMap.SimpleImmutableEntry<>(name, t));
            }
        }
    }

    private void checkForm() {
        if (form == null) {
            form = new ArrayList<>();
        }
    }

    public void addRequestParams(String name, Object value, Object defaultValue) {
        checkRequestParams();
        requestParams.add(new AbstractMap.SimpleImmutableEntry<>(name, value == null ? defaultValue : value));
    }

    public void addRequestParams(String name, Object value) {
        checkRequestParams();
        requestParams.add(new AbstractMap.SimpleImmutableEntry<>(name, value));
    }

    public <T> void addRequestParams(String name, Collection<T> value) {
        checkRequestParams();
        if (value != null && value.size() > 0) {
            for (T t : value) {
                requestParams.add(new AbstractMap.SimpleImmutableEntry<>(name, t));
            }
        }
    }

    public <T> void addRequestParams(String name, T[] value) {
        checkRequestParams();
        if (value != null && value.length > 0) {
            for (T t : value) {
                requestParams.add(new AbstractMap.SimpleImmutableEntry<>(name, t));
            }
        }
    }

    public void addRequestParams(String name, byte[] value) {
        checkRequestParams();
        if (value != null && value.length > 0) {
            for (byte t : value) {
                requestParams.add(new AbstractMap.SimpleImmutableEntry<>(name, t));
            }
        }
    }

    public void addRequestParams(String name, short[] value) {
        checkRequestParams();
        if (value != null && value.length > 0) {
            for (short t : value) {
                requestParams.add(new AbstractMap.SimpleImmutableEntry<>(name, t));
            }
        }
    }

    public void addRequestParams(String name, int[] value) {
        checkRequestParams();
        if (value != null && value.length > 0) {
            for (int t : value) {
                requestParams.add(new AbstractMap.SimpleImmutableEntry<>(name, t));
            }
        }
    }

    public void addRequestParams(String name, long[] value) {
        checkRequestParams();
        if (value != null && value.length > 0) {
            for (long t : value) {
                requestParams.add(new AbstractMap.SimpleImmutableEntry<>(name, t));
            }
        }
    }

    public void addRequestParams(String name, float[] value) {
        checkRequestParams();
        if (value != null && value.length > 0) {
            for (float t : value) {
                requestParams.add(new AbstractMap.SimpleImmutableEntry<>(name, t));
            }
        }
    }

    public void addRequestParams(String name, double[] value) {
        checkRequestParams();
        if (value != null && value.length > 0) {
            for (double t : value) {
                requestParams.add(new AbstractMap.SimpleImmutableEntry<>(name, t));
            }
        }
    }

    public void addRequestParams(String name, boolean[] value) {
        checkRequestParams();
        if (value != null && value.length > 0) {
            for (boolean t : value) {
                requestParams.add(new AbstractMap.SimpleImmutableEntry<>(name, t));
            }
        }
    }

    private void checkRequestParams() {
        if (requestParams == null) {
            requestParams = new ArrayList<>();
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


    public void addHeader(String name, Object value, Object defaultValue) {
        if (header == null) {
            header = new ArrayList<>();
        }
        header.add(new AbstractMap.SimpleImmutableEntry<>(name, value == null ? defaultValue : value));
    }

    public void addHeader(String name, Object[] value) {
        checkRequestParams();
        if (value != null && value.length > 0) {
            for (Object t : value) {
                requestParams.add(new AbstractMap.SimpleImmutableEntry<>(name, t));
            }
        }
    }

    public List<Map.Entry<String, Object>> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(List<Map.Entry<String, Object>> requestParams) {
        this.requestParams = requestParams;
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
                ", requestParams=" + requestParams +
                ", uriVariables=" + uriVariables +
                ", header=" + header +
                ", requestType=" + requestType +
                ", type=" + type +
                ", isAccount=" + isAccount +
                '}';
    }

}
