package org.forkjoin.scrat.apikit.example.client.model;

import org.forkjoin.scrat.apikit.example.client.core.BaseObj;
import org.forkjoin.scrat.apikit.example.client.core.TestModel;

import java.util.*;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleImmutableEntry;

/**
 * 
 */
public class ObjectModel extends BaseObj<Integer> {

	private TestModel test;

	private TestArrayModel testArray;

	private TestWrapperModel testWrapper;

	public ObjectModel() {
	}

	public ObjectModel(TestModel test, TestArrayModel testArray, TestWrapperModel testWrapper) {
		this.test = test;
		this.testArray = testArray;
		this.testWrapper = testWrapper;
	}

	public TestModel getTest() {
		return test;
	}

	public void setTest(TestModel test) {
		this.test = test;
	}

	public TestArrayModel getTestArray() {
		return testArray;
	}

	public void setTestArray(TestArrayModel testArray) {
		this.testArray = testArray;
	}

	public TestWrapperModel getTestWrapper() {
		return testWrapper;
	}

	public void setTestWrapper(TestWrapperModel testWrapper) {
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
		return "ObjectModel [test=" + test + ",testArray=" + testArray + ",testWrapper=" + testWrapper + ", ]";
	}
}