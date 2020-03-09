package org.forkjoin.scrat.apikit.example.model;

import org.forkjoin.BaseObj;
import org.forkjoin.TestModel;
import org.forkjoin.scrat.apikit.example.TestArrayForm;
import org.forkjoin.scrat.apikit.example.form.child.TestArrayModel;
import org.forkjoin.scrat.apikit.example.model.child.GenericValue;
import org.forkjoin.scrat.apikit.example.type.StatusType;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 帮助
 */
public class ObjectModel extends BaseObj<Long> {
    private LinkedHashMap<String,BaseObj<Long>> objectMap;
    private LinkedHashMap<String,ObjectModel[]> objectArrayMap;
    private LinkedHashMap<String,List<BaseObj<Long>>> objectListMap;
    private HashMap<String,Float> floatMap;
    private HashMap<Float,Float> floatFloatMap;
    private HashMap<Float,Float[]> floatFloatArrayMap;
    private HashMap<Float, List<Float>> floatFloatListMap;
    private HashMap<String,String> stringMap;
//    private HashMap<StatusType,StatusType> statusTypeMap;
//    private HashMap<StatusType,List<StatusType>> statusTypeListMap;
//    private HashMap<StatusType,StatusType[]> statusTypeArrayMap;

    private StatusType statusType;
    private StatusType[] statusTypeArray;
    private List<StatusType> statusTypeList;

    /**
     * test1
     */
    private TestModel test;
    private boolean sb;
    private GenericValue v1;
    private GenericValue<Date, byte[]> v2;

    private GenericValue<Date, byte[]> v3;
    private TestArrayModel testArrayModel;
    private TestArrayForm testArrayForm;
    private TestWrapperModel testWrapperModel;



    public TestModel getTest() {
        return test;
    }

    public void setTest(TestModel test) {
        this.test = test;
    }

    public GenericValue getV1() {
        return v1;
    }

    public void setV1(GenericValue v1) {
        this.v1 = v1;
    }

//    public GenericValue<Date, byte[]> getV2() {
//        return v2;
//    }
//
//    public void setV2(GenericValue<Date, byte[]> v2) {
//        this.v2 = v2;
//    }

//    public GenericValue<Date, ?> getV3() {
//        return v3;
//    }
//
//    public void setV3(GenericValue<Date, ?> v3) {
//        this.v3 = v3;
//    }


    public LinkedHashMap<String, BaseObj<Long>> getObjectMap() {
        return objectMap;
    }

    public void setObjectMap(LinkedHashMap<String, BaseObj<Long>> objectMap) {
        this.objectMap = objectMap;
    }

    public LinkedHashMap<String, ObjectModel[]> getObjectArrayMap() {
        return objectArrayMap;
    }

    public void setObjectArrayMap(LinkedHashMap<String, ObjectModel[]> objectArrayMap) {
        this.objectArrayMap = objectArrayMap;
    }

    public LinkedHashMap<String, List<BaseObj<Long>>> getObjectListMap() {
        return objectListMap;
    }

    public void setObjectListMap(LinkedHashMap<String, List<BaseObj<Long>>> objectListMap) {
        this.objectListMap = objectListMap;
    }

    public HashMap<String, Float> getFloatMap() {
        return floatMap;
    }

    public void setFloatMap(HashMap<String, Float> floatMap) {
        this.floatMap = floatMap;
    }

    public HashMap<Float, Float> getFloatFloatMap() {
        return floatFloatMap;
    }

    public void setFloatFloatMap(HashMap<Float, Float> floatFloatMap) {
        this.floatFloatMap = floatFloatMap;
    }

    public HashMap<Float, Float[]> getFloatFloatArrayMap() {
        return floatFloatArrayMap;
    }

    public void setFloatFloatArrayMap(HashMap<Float, Float[]> floatFloatArrayMap) {
        this.floatFloatArrayMap = floatFloatArrayMap;
    }

    public HashMap<Float, List<Float>> getFloatFloatListMap() {
        return floatFloatListMap;
    }

    public void setFloatFloatListMap(HashMap<Float, List<Float>> floatFloatListMap) {
        this.floatFloatListMap = floatFloatListMap;
    }

    public HashMap<String, String> getStringMap() {
        return stringMap;
    }

    public void setStringMap(HashMap<String, String> stringMap) {
        this.stringMap = stringMap;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public StatusType[] getStatusTypeArray() {
        return statusTypeArray;
    }

    public void setStatusTypeArray(StatusType[] statusTypeArray) {
        this.statusTypeArray = statusTypeArray;
    }

    public List<StatusType> getStatusTypeList() {
        return statusTypeList;
    }

    public void setStatusTypeList(List<StatusType> statusTypeList) {
        this.statusTypeList = statusTypeList;
    }

    public boolean isSb() {
        return sb;
    }

    public void setSb(boolean sb) {
        this.sb = sb;
    }

    public GenericValue<Date, byte[]> getV2() {
        return v2;
    }

    public void setV2(GenericValue<Date, byte[]> v2) {
        this.v2 = v2;
    }

    public GenericValue<Date, byte[]> getV3() {
        return v3;
    }

    public void setV3(GenericValue<Date, byte[]> v3) {
        this.v3 = v3;
    }

    public TestArrayModel getTestArrayModel() {
        return testArrayModel;
    }

    public void setTestArrayModel(TestArrayModel testArrayModel) {
        this.testArrayModel = testArrayModel;
    }

    public TestArrayForm getTestArrayForm() {
        return testArrayForm;
    }

    public void setTestArrayForm(TestArrayForm testArrayForm) {
        this.testArrayForm = testArrayForm;
    }

    public TestWrapperModel getTestWrapperModel() {
        return testWrapperModel;
    }

    public void setTestWrapperModel(TestWrapperModel testWrapperModel) {
        this.testWrapperModel = testWrapperModel;
    }
}
