package org.forkjoin;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.StringJoiner;

public class TestForm {
    private long longValue;
    private int intValue;
    private short shortValue;
    private byte byteValue;
    private char charValue;
    private float floatValue;
    private double doubleValue;
    private boolean booleanValue;
    private String stringValue;
    private byte[] bytesValue;
    private Date data;
    private LocalDateTime localDateTime;


    public long getLongValue() {
        return longValue;
    }

    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public short getShortValue() {
        return shortValue;
    }

    public void setShortValue(short shortValue) {
        this.shortValue = shortValue;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public void setByteValue(byte byteValue) {
        this.byteValue = byteValue;
    }

    public char getCharValue() {
        return charValue;
    }

    public void setCharValue(char charValue) {
        this.charValue = charValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public byte[] getBytesValue() {
        return bytesValue;
    }

    public void setBytesValue(byte[] bytesValue) {
        this.bytesValue = bytesValue;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TestForm.class.getSimpleName() + "[", "]")
                .add("longValue=" + longValue)
                .add("intValue=" + intValue)
                .add("shortValue=" + shortValue)
                .add("byteValue=" + byteValue)
                .add("charValue=" + charValue)
                .add("floatValue=" + floatValue)
                .add("doubleValue=" + doubleValue)
                .add("booleanValue=" + booleanValue)
                .add("stringValue='" + stringValue + "'")
                .add("bytesValue=" + Arrays.toString(bytesValue))
                .add("data=" + data)
                .toString();
    }
}

