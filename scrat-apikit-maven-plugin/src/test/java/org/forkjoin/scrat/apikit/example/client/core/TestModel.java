package org.forkjoin.scrat.apikit.example.client.core;

import java.util.*;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleImmutableEntry;

/**
 * 
 */
public class TestModel<T> extends BaseObj<T> {

	private boolean booleanValue;

	private byte byteValue;

	private double charValue;

	private double doubleValue;

	private float floatValue;

	private int intValue;

	private long longValue;

	private short shortValue;

	private BaseObj<String> stringBaseObj;

	private String stringValue;

	public TestModel() {
	}

	public TestModel(boolean booleanValue, byte byteValue, double charValue, double doubleValue, float floatValue,
			int intValue, long longValue, short shortValue, BaseObj<String> stringBaseObj, String stringValue) {
		this.booleanValue = booleanValue;
		this.byteValue = byteValue;
		this.charValue = charValue;
		this.doubleValue = doubleValue;
		this.floatValue = floatValue;
		this.intValue = intValue;
		this.longValue = longValue;
		this.shortValue = shortValue;
		this.stringBaseObj = stringBaseObj;
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

	public BaseObj<String> getStringBaseObj() {
		return stringBaseObj;
	}

	public void setStringBaseObj(BaseObj<String> stringBaseObj) {
		this.stringBaseObj = stringBaseObj;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public List<Entry<String, Object>> encode(String $parent, List<Entry<String, Object>> $list) {
		throw new RuntimeException("不支持泛型");
	}

	@Override
	public String toString() {
		return "TestModel [booleanValue=" + booleanValue + ",byteValue=" + byteValue + ",charValue=" + charValue
				+ ",doubleValue=" + doubleValue + ",floatValue=" + floatValue + ",intValue=" + intValue + ",longValue="
				+ longValue + ",shortValue=" + shortValue + ",stringBaseObj=" + stringBaseObj + ",stringValue="
				+ stringValue + ", ]";
	}
}