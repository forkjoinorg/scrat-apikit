package org.forkjoin.scrat.apikit.example.model;

import org.forkjoin.BaseObj;
import org.forkjoin.TestModel;
import org.forkjoin.scrat.apikit.example.type.StatusType;

public class ObjectModel extends BaseObj<Integer>{
    private TestArrayModel testArray;
    private TestModel test;
    private TestWrapperModel testWrapper;
    private StatusType statusType;

    public TestArrayModel getTestArray() {
        return testArray;
    }

    public void setTestArray(TestArrayModel testArray) {
        this.testArray = testArray;
    }

    public TestModel getTest() {
        return test;
    }

    public void setTest(TestModel test) {
        this.test = test;
    }

    public TestWrapperModel getTestWrapper() {
        return testWrapper;
    }

    public void setTestWrapper(TestWrapperModel testWrapper) {
        this.testWrapper = testWrapper;
    }


    @Override
    public String toString() {
        return "ObjectModel{" +
                "testArray=" + testArray +
                ", test=" + test +
                ", testWrapper=" + testWrapper +
                '}';
    }
}
