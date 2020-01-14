package org.forkjoin.scrat.apikit.example.model.child;

import org.forkjoin.BaseObj;

public class GenericValue<T,V> {
    private T data;
    private V v;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public V getV() {
        return v;
    }

    public void setV(V v) {
        this.v = v;
    }
}
