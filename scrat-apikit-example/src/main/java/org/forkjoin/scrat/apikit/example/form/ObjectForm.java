package org.forkjoin.scrat.apikit.example.form;

import org.forkjoin.TestForm;
import org.forkjoin.scrat.apikit.example.TestArrayForm;
import org.forkjoin.scrat.apikit.example.type.StatusType;

public class ObjectForm {
    private String pUserId;
    private TestArrayForm testArray;
    private TestForm test;
    private StatusType statusType;
    private TestWrapperForm testWrapper;


    public TestArrayForm getTestArray() {
        return testArray;
    }

    public void setTestArray(TestArrayForm testArray) {
        this.testArray = testArray;
    }

    public TestForm getTest() {
        return test;
    }

    public void setTest(TestForm test) {
        this.test = test;
    }

    public TestWrapperForm getTestWrapper() {
        return testWrapper;
    }

    public void setTestWrapper(TestWrapperForm testWrapper) {
        this.testWrapper = testWrapper;
    }

    public String getpUserId() {
        return pUserId;
    }

    public void setpUserId(String pUserId) {
        this.pUserId = pUserId;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
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
