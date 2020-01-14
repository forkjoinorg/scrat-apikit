package org.forkjoin.scrat.apikit.example.model;

import org.forkjoin.BaseObj;
import org.forkjoin.TestModel;
import org.forkjoin.scrat.apikit.example.TestArrayForm;
import org.forkjoin.scrat.apikit.example.form.child.TestArrayModel;
import org.forkjoin.scrat.apikit.example.model.child.GenericValue;

import java.util.Date;

/**
 * 帮助
 */
public class ObjectModel extends BaseObj<Number> {
    /**
     * test1
     */
    private TestModel test;
    private GenericValue v1;
    private GenericValue<Date, byte[]> v2;

    private GenericValue<Date, byte[]> v3;
    private TestArrayModel testArrayModel;
    private TestArrayForm testArrayForm;


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


    /**
     * v2
     * @return v2
     */
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
}
