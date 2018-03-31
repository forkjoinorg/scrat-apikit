package org.forkjoin.scrat.apikit.example.client.model;

import org.forkjoin.scrat.apikit.example.client.core.BaseObj;

import java.util.*;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleImmutableEntry;

/**
 * 
 */
public class GenericModel<T> {

	private BaseObj<String> baseObj;

	private T data;

	public GenericModel() {
	}

	public GenericModel(BaseObj<String> baseObj, T data) {
		this.baseObj = baseObj;
		this.data = data;
	}

	public BaseObj<String> getBaseObj() {
		return baseObj;
	}

	public void setBaseObj(BaseObj<String> baseObj) {
		this.baseObj = baseObj;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<Entry<String, Object>> encode(String $parent, List<Entry<String, Object>> $list) {
		throw new RuntimeException("不支持泛型");
	}

	@Override
	public String toString() {
		return "GenericModel [baseObj=" + baseObj + ",data=" + data + ", ]";
	}
}