package org.forkjoin.scrat.apikit.example.form;


import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public class FilterListFrom {
    private Pageable pageable;
    private ObjectId objectId;
    private Map<String,ObjectId> objectIdMap;
    private Map<String,Pageable> pageableMap;

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public Map<String, ObjectId> getObjectIdMap() {
        return objectIdMap;
    }

    public void setObjectIdMap(Map<String, ObjectId> objectIdMap) {
        this.objectIdMap = objectIdMap;
    }

    public Map<String, Pageable> getPageableMap() {
        return pageableMap;
    }

    public void setPageableMap(Map<String, Pageable> pageableMap) {
        this.pageableMap = pageableMap;
    }
}
