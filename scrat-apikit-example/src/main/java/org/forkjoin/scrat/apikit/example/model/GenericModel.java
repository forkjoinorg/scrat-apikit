package org.forkjoin.scrat.apikit.example.model;

import org.forkjoin.BaseObj;

public class GenericModel<T> {
    private T data;
    private BaseObj<String> baseObj;

    public BaseObj<String> getBaseObj() {
        return baseObj;
    }

    public void setBaseObj(BaseObj<String> baseObj) {
        this.baseObj = baseObj;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GenericModel{" +
                "data=" + data +
                '}';
    }
}
