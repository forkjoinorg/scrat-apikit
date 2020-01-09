package org.forkjoin;

import java.util.List;
import java.util.Map;

public class BaseObj<T>  {
    private T baseName;

    public T getBaseName() {
        return baseName;
    }

    public void setBaseName(T baseName) {
        this.baseName = baseName;
    }

    public List<Map.Entry<String, Object>> encode(String $parent, List<Map.Entry<String, Object>> $list) {
        return $list;
    }
}
