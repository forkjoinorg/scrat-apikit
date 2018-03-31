package org.forkjoin.scrat.apikit.example.form;

public class GenericForm<T> {
    private T data;

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
