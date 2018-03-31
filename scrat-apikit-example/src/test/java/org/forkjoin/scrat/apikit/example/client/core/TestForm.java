package org.forkjoin.scrat.apikit.example.client.core;

import java.util.*;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleImmutableEntry;

/**
 * 
 */
public class TestForm extends BaseObj<String> {

	private boolean booleanValue;

	private byte byteValue;

	private double charValue;

	private double doubleValue;

	private float floatValue;

	private int intValue;

	private long longValue;

	private short shortValue;

	private String stringValue;

	public TestForm() {
	}

	public TestForm(boolean booleanValue, byte byteValue, double charValue, double doubleValue, float floatValue,
			int intValue, long longValue, short shortValue, String stringValue) {
		this.booleanValue = booleanValue;
		this.byteValue = byteValue;
		this.charValue = charValue;
		this.doubleValue = doubleValue;
		this.floatValue = floatValue;
		this.intValue = intValue;
		this.longValue = longValue;
		this.shortValue = shortValue;
		this.stringValue = stringValue;
	}

	public boolean getBooleanValue() {
		return booleanValue;
	}

	public void setBooleanValue(boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	public byte getByteValue() {
		return byteValue;
	}

	public void setByteValue(byte byteValue) {
		this.byteValue = byteValue;
	}

	public double getCharValue() {
		return charValue;
	}

	public void setCharValue(double charValue) {
		this.charValue = charValue;
	}

	public double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
	}

	public float getFloatValue() {
		return floatValue;
	}

	public void setFloatValue(float floatValue) {
		this.floatValue = floatValue;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public long getLongValue() {
		return longValue;
	}

	public void setLongValue(long longValue) {
		this.longValue = longValue;
	}

	public short getShortValue() {
		return shortValue;
	}

	public void setShortValue(short shortValue) {
		this.shortValue = shortValue;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public List<Entry<String, Object>> encode(String $parent, List<Entry<String, Object>> $list) {

		$list.add(new SimpleImmutableEntry<>($parent + "booleanValue", booleanValue));

		$list.add(new SimpleImmutableEntry<>($parent + "byteValue", byteValue));

		$list.add(new SimpleImmutableEntry<>($parent + "charValue", charValue));

		$list.add(new SimpleImmutableEntry<>($parent + "doubleValue", doubleValue));

		$list.add(new SimpleImmutableEntry<>($parent + "floatValue", floatValue));

		$list.add(new SimpleImmutableEntry<>($parent + "intValue", intValue));

		$list.add(new SimpleImmutableEntry<>($parent + "longValue", longValue));

		$list.add(new SimpleImmutableEntry<>($parent + "shortValue", shortValue));

		if (stringValue != null) {
			$list.add(new SimpleImmutableEntry<>($parent + "stringValue", stringValue));
		}
		return $list;
	}

	@Override
	public String toString() {
		return "TestForm [booleanValue=" + booleanValue + ",byteValue=" + byteValue + ",charValue=" + charValue
				+ ",doubleValue=" + doubleValue + ",floatValue=" + floatValue + ",intValue=" + intValue + ",longValue="
				+ longValue + ",shortValue=" + shortValue + ",stringValue=" + stringValue + ", ]";
	}
}