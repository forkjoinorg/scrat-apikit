package org.forkjoin.scrat.apikit.example.client.core;

import java.util.*;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleImmutableEntry;

/**
 * 
 */
public class BaseObj<T> {

	private T baseName;

	public BaseObj() {
	}

	public BaseObj(T baseName) {
		this.baseName = baseName;
	}

	public T getBaseName() {
		return baseName;
	}

	public void setBaseName(T baseName) {
		this.baseName = baseName;
	}

	public List<Entry<String, Object>> encode(String $parent, List<Entry<String, Object>> $list) {
		throw new RuntimeException("不支持泛型");
	}

	@Override
	public String toString() {
		return "BaseObj [baseName=" + baseName + ", ]";
	}
}