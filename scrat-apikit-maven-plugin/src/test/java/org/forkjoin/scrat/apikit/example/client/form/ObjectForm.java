package org.forkjoin.scrat.apikit.example.client.form;

import org.forkjoin.scrat.apikit.example.client.core.TestForm;

import java.util.*;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleImmutableEntry;

/**
 * 
 */
public class ObjectForm {

	private TestForm test;

	private TestArrayForm testArray;

	private TestWrapperForm testWrapper;

	public ObjectForm() {
	}

	public ObjectForm(TestForm test, TestArrayForm testArray, TestWrapperForm testWrapper) {
		this.test = test;
		this.testArray = testArray;
		this.testWrapper = testWrapper;
	}

	public TestForm getTest() {
		return test;
	}

	public void setTest(TestForm test) {
		this.test = test;
	}

	public TestArrayForm getTestArray() {
		return testArray;
	}

	public void setTestArray(TestArrayForm testArray) {
		this.testArray = testArray;
	}

	public TestWrapperForm getTestWrapper() {
		return testWrapper;
	}

	public void setTestWrapper(TestWrapperForm testWrapper) {
		this.testWrapper = testWrapper;
	}

	public List<Entry<String, Object>> encode(String $parent, List<Entry<String, Object>> $list) {

		if (test != null) {
			test.encode($parent + "test.", $list);
		}

		if (testArray != null) {
			testArray.encode($parent + "testArray.", $list);
		}

		if (testWrapper != null) {
			testWrapper.encode($parent + "testWrapper.", $list);
		}
		return $list;
	}

	@Override
	public String toString() {
		return "ObjectForm [test=" + test + ",testArray=" + testArray + ",testWrapper=" + testWrapper + ", ]";
	}
}