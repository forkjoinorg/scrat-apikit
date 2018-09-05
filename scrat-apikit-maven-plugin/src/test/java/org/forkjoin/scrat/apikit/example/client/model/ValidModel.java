package org.forkjoin.scrat.apikit.example.client.model;

import java.util.*;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleImmutableEntry;

/**
 * 
 */
public class ValidModel {

	private String name;

	public ValidModel() {
	}

	public ValidModel(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Entry<String, Object>> encode(String $parent, List<Entry<String, Object>> $list) {

		if (name != null) {
			$list.add(new SimpleImmutableEntry<>($parent + "name", name));
		}
		return $list;
	}

	@Override
	public String toString() {
		return "ValidModel [name=" + name + ", ]";
	}
}