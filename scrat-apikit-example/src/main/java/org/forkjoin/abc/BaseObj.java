package org.forkjoin.abc;

public class BaseObj<T>  {
    private T baseName;

    public T getBaseName() {
        return baseName;
    }

    public void setBaseName(T baseName) {
        this.baseName = baseName;
    }
}
