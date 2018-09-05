package org.forkjoin.scrat.apikit.example.client.form;

import java.util.*;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleImmutableEntry;

/**
 * 
 */
public class GenericForm<T> {

	private T data;

	public GenericForm() {
	}

	public GenericForm(T data) {
		this.data = data;
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
		return "GenericForm [data=" + data + ", ]";
	}
}