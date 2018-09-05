package org.forkjoin.scrat.apikit.example.client.form;

import java.util.*;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleImmutableEntry;

/**
 * 
 */
public class TestArrayForm {

	private java.util.ArrayList<Boolean> booleanValueArray;

	private List<Boolean> booleanValues;

	private byte[] byteValueArray;

	private List<Byte> byteValues;

	private java.util.ArrayList<Double> charValueArray;

	private List<String> charValues;

	private java.util.ArrayList<Double> doubleValueArray;

	private List<Double> doubleValues;

	private java.util.ArrayList<Float> floatValueArray;

	private List<Float> floatValues;

	private java.util.ArrayList<Integer> intValueArray;

	private List<Integer> intValues;

	private java.util.ArrayList<Long> longValueArray;

	private List<Long> longValues;

	private java.util.ArrayList<Short> shortValueArray;

	private List<Short> shortValues;

	private java.util.ArrayList<String> stringValueArray;

	private List<String> stringValues;

	public TestArrayForm() {
	}

	public TestArrayForm(java.util.ArrayList<Boolean> booleanValueArray, List<Boolean> booleanValues,
			byte[] byteValueArray, List<Byte> byteValues, java.util.ArrayList<Double> charValueArray,
			List<String> charValues, java.util.ArrayList<Double> doubleValueArray, List<Double> doubleValues,
			java.util.ArrayList<Float> floatValueArray, List<Float> floatValues,
			java.util.ArrayList<Integer> intValueArray, List<Integer> intValues,
			java.util.ArrayList<Long> longValueArray, List<Long> longValues, java.util.ArrayList<Short> shortValueArray,
			List<Short> shortValues, java.util.ArrayList<String> stringValueArray, List<String> stringValues) {
		this.booleanValueArray = booleanValueArray;
		this.booleanValues = booleanValues;
		this.byteValueArray = byteValueArray;
		this.byteValues = byteValues;
		this.charValueArray = charValueArray;
		this.charValues = charValues;
		this.doubleValueArray = doubleValueArray;
		this.doubleValues = doubleValues;
		this.floatValueArray = floatValueArray;
		this.floatValues = floatValues;
		this.intValueArray = intValueArray;
		this.intValues = intValues;
		this.longValueArray = longValueArray;
		this.longValues = longValues;
		this.shortValueArray = shortValueArray;
		this.shortValues = shortValues;
		this.stringValueArray = stringValueArray;
		this.stringValues = stringValues;
	}

	public java.util.ArrayList<Boolean> getBooleanValueArray() {
		return booleanValueArray;
	}

	public void setBooleanValueArray(java.util.ArrayList<Boolean> booleanValueArray) {
		this.booleanValueArray = booleanValueArray;
	}

	public List<Boolean> getBooleanValues() {
		return booleanValues;
	}

	public void setBooleanValues(List<Boolean> booleanValues) {
		this.booleanValues = booleanValues;
	}

	public byte[] getByteValueArray() {
		return byteValueArray;
	}

	public void setByteValueArray(byte[] byteValueArray) {
		this.byteValueArray = byteValueArray;
	}

	public List<Byte> getByteValues() {
		return byteValues;
	}

	public void setByteValues(List<Byte> byteValues) {
		this.byteValues = byteValues;
	}

	public java.util.ArrayList<Double> getCharValueArray() {
		return charValueArray;
	}

	public void setCharValueArray(java.util.ArrayList<Double> charValueArray) {
		this.charValueArray = charValueArray;
	}

	public List<String> getCharValues() {
		return charValues;
	}

	public void setCharValues(List<String> charValues) {
		this.charValues = charValues;
	}

	public java.util.ArrayList<Double> getDoubleValueArray() {
		return doubleValueArray;
	}

	public void setDoubleValueArray(java.util.ArrayList<Double> doubleValueArray) {
		this.doubleValueArray = doubleValueArray;
	}

	public List<Double> getDoubleValues() {
		return doubleValues;
	}

	public void setDoubleValues(List<Double> doubleValues) {
		this.doubleValues = doubleValues;
	}

	public java.util.ArrayList<Float> getFloatValueArray() {
		return floatValueArray;
	}

	public void setFloatValueArray(java.util.ArrayList<Float> floatValueArray) {
		this.floatValueArray = floatValueArray;
	}

	public List<Float> getFloatValues() {
		return floatValues;
	}

	public void setFloatValues(List<Float> floatValues) {
		this.floatValues = floatValues;
	}

	public java.util.ArrayList<Integer> getIntValueArray() {
		return intValueArray;
	}

	public void setIntValueArray(java.util.ArrayList<Integer> intValueArray) {
		this.intValueArray = intValueArray;
	}

	public List<Integer> getIntValues() {
		return intValues;
	}

	public void setIntValues(List<Integer> intValues) {
		this.intValues = intValues;
	}

	public java.util.ArrayList<Long> getLongValueArray() {
		return longValueArray;
	}

	public void setLongValueArray(java.util.ArrayList<Long> longValueArray) {
		this.longValueArray = longValueArray;
	}

	public List<Long> getLongValues() {
		return longValues;
	}

	public void setLongValues(List<Long> longValues) {
		this.longValues = longValues;
	}

	public java.util.ArrayList<Short> getShortValueArray() {
		return shortValueArray;
	}

	public void setShortValueArray(java.util.ArrayList<Short> shortValueArray) {
		this.shortValueArray = shortValueArray;
	}

	public List<Short> getShortValues() {
		return shortValues;
	}

	public void setShortValues(List<Short> shortValues) {
		this.shortValues = shortValues;
	}

	public java.util.ArrayList<String> getStringValueArray() {
		return stringValueArray;
	}

	public void setStringValueArray(java.util.ArrayList<String> stringValueArray) {
		this.stringValueArray = stringValueArray;
	}

	public List<String> getStringValues() {
		return stringValues;
	}

	public void setStringValues(List<String> stringValues) {
		this.stringValues = stringValues;
	}

	public void addBooleanValueArray(boolean booleanValueArray) {
		if (this.booleanValueArray == null) {
			this.booleanValueArray = new java.util.ArrayList<Boolean>();
		}
		this.booleanValueArray.add(booleanValueArray);
	}

	public void addCharValueArray(double charValueArray) {
		if (this.charValueArray == null) {
			this.charValueArray = new java.util.ArrayList<Double>();
		}
		this.charValueArray.add(charValueArray);
	}

	public void addDoubleValueArray(double doubleValueArray) {
		if (this.doubleValueArray == null) {
			this.doubleValueArray = new java.util.ArrayList<Double>();
		}
		this.doubleValueArray.add(doubleValueArray);
	}

	public void addFloatValueArray(float floatValueArray) {
		if (this.floatValueArray == null) {
			this.floatValueArray = new java.util.ArrayList<Float>();
		}
		this.floatValueArray.add(floatValueArray);
	}

	public void addIntValueArray(int intValueArray) {
		if (this.intValueArray == null) {
			this.intValueArray = new java.util.ArrayList<Integer>();
		}
		this.intValueArray.add(intValueArray);
	}

	public void addLongValueArray(long longValueArray) {
		if (this.longValueArray == null) {
			this.longValueArray = new java.util.ArrayList<Long>();
		}
		this.longValueArray.add(longValueArray);
	}

	public void addShortValueArray(short shortValueArray) {
		if (this.shortValueArray == null) {
			this.shortValueArray = new java.util.ArrayList<Short>();
		}
		this.shortValueArray.add(shortValueArray);
	}

	public void addStringValueArray(String stringValueArray) {
		if (this.stringValueArray == null) {
			this.stringValueArray = new java.util.ArrayList<String>();
		}
		this.stringValueArray.add(stringValueArray);
	}

	public List<Entry<String, Object>> encode(String $parent, List<Entry<String, Object>> $list) {

		if (booleanValueArray != null && (!booleanValueArray.isEmpty())) {
			$list.add(new SimpleImmutableEntry<>($parent + "booleanValueArray", booleanValueArray));
		}

		if (booleanValues != null && (!booleanValues.isEmpty())) {
			$list.add(new SimpleImmutableEntry<>($parent + "booleanValues", booleanValues));
		}

		if (byteValueArray != null && (byteValueArray.length > 0)) {
			$list.add(new SimpleImmutableEntry<>($parent + "byteValueArray", byteValueArray));
		}

		if (byteValues != null && (!byteValues.isEmpty())) {
			$list.add(new SimpleImmutableEntry<>($parent + "byteValues", byteValues));
		}

		if (charValueArray != null && (!charValueArray.isEmpty())) {
			$list.add(new SimpleImmutableEntry<>($parent + "charValueArray", charValueArray));
		}

		if (charValues != null && (!charValues.isEmpty())) {
			for (int i = 0; i < charValues.size(); i++) {
				$list.add(new SimpleImmutableEntry<>($parent + "charValues", charValues.get(i)));
			}
		}

		if (doubleValueArray != null && (!doubleValueArray.isEmpty())) {
			$list.add(new SimpleImmutableEntry<>($parent + "doubleValueArray", doubleValueArray));
		}

		if (doubleValues != null && (!doubleValues.isEmpty())) {
			$list.add(new SimpleImmutableEntry<>($parent + "doubleValues", doubleValues));
		}

		if (floatValueArray != null && (!floatValueArray.isEmpty())) {
			$list.add(new SimpleImmutableEntry<>($parent + "floatValueArray", floatValueArray));
		}

		if (floatValues != null && (!floatValues.isEmpty())) {
			$list.add(new SimpleImmutableEntry<>($parent + "floatValues", floatValues));
		}

		if (intValueArray != null && (!intValueArray.isEmpty())) {
			$list.add(new SimpleImmutableEntry<>($parent + "intValueArray", intValueArray));
		}

		if (intValues != null && (!intValues.isEmpty())) {
			$list.add(new SimpleImmutableEntry<>($parent + "intValues", intValues));
		}

		if (longValueArray != null && (!longValueArray.isEmpty())) {
			$list.add(new SimpleImmutableEntry<>($parent + "longValueArray", longValueArray));
		}

		if (longValues != null && (!longValues.isEmpty())) {
			$list.add(new SimpleImmutableEntry<>($parent + "longValues", longValues));
		}

		if (shortValueArray != null && (!shortValueArray.isEmpty())) {
			$list.add(new SimpleImmutableEntry<>($parent + "shortValueArray", shortValueArray));
		}

		if (shortValues != null && (!shortValues.isEmpty())) {
			$list.add(new SimpleImmutableEntry<>($parent + "shortValues", shortValues));
		}

		if (stringValueArray != null && (!stringValueArray.isEmpty())) {
			for (int i = 0; i < stringValueArray.size(); i++) {
				$list.add(new SimpleImmutableEntry<>($parent + "stringValueArray", stringValueArray.get(i)));
			}
		}

		if (stringValues != null && (!stringValues.isEmpty())) {
			for (int i = 0; i < stringValues.size(); i++) {
				$list.add(new SimpleImmutableEntry<>($parent + "stringValues", stringValues.get(i)));
			}
		}
		return $list;
	}

	@Override
	public String toString() {
		return "TestArrayForm [booleanValueArray=size:" + (booleanValueArray == null ? -1 : booleanValueArray.size())
				+ ",booleanValues=" + booleanValues + ",byteValueArray=length:"
				+ (byteValueArray == null ? -1 : byteValueArray.length) + ",byteValues=" + byteValues
				+ ",charValueArray=size:" + (charValueArray == null ? -1 : charValueArray.size()) + ",charValues="
				+ charValues + ",doubleValueArray=size:" + (doubleValueArray == null ? -1 : doubleValueArray.size())
				+ ",doubleValues=" + doubleValues + ",floatValueArray=size:"
				+ (floatValueArray == null ? -1 : floatValueArray.size()) + ",floatValues=" + floatValues
				+ ",intValueArray=size:" + (intValueArray == null ? -1 : intValueArray.size()) + ",intValues="
				+ intValues + ",longValueArray=size:" + (longValueArray == null ? -1 : longValueArray.size())
				+ ",longValues=" + longValues + ",shortValueArray=size:"
				+ (shortValueArray == null ? -1 : shortValueArray.size()) + ",shortValues=" + shortValues
				+ ",stringValueArray=size:" + (stringValueArray == null ? -1 : stringValueArray.size())
				+ ",stringValues=" + stringValues + ", ]";
	}
}