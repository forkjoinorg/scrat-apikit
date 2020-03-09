package org.forkjoin.scrat.apikit.example.form.child;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class TestArrayModel {
    private long[] longValueArray;
    private int[] intValueArray;
    private short[] shortValueArray;
    private byte[] byteValueArray;
    private char[] charValueArray;
    private float[] floatValueArray;
    private double[] doubleValueArray;
    private boolean[] booleanValueArray;
    private String[] stringValueArray;
    private TestArrayModel[] objectValueArray;
    private LocalDateTime[] dateValueArray;

    private List<Long> longValues;
    private List<Integer> intValues;
    private List<Short> shortValues;
    private List<Byte> byteValues;
    private List<Character> charValues;
    private List<Float> floatValues;
    private List<Double> doubleValues;
    private List<Boolean> booleanValues;
    private List<String> stringValues;
    private List<LocalDateTime> dateValues;
    private List<TestArrayModel> objectValues;

    public long[] getLongValueArray() {
        return longValueArray;
    }

    public void setLongValueArray(long[] longValueArray) {
        this.longValueArray = longValueArray;
    }

    public int[] getIntValueArray() {
        return intValueArray;
    }

    public void setIntValueArray(int[] intValueArray) {
        this.intValueArray = intValueArray;
    }

    public short[] getShortValueArray() {
        return shortValueArray;
    }

    public void setShortValueArray(short[] shortValueArray) {
        this.shortValueArray = shortValueArray;
    }

    public byte[] getByteValueArray() {
        return byteValueArray;
    }

    public void setByteValueArray(byte[] byteValueArray) {
        this.byteValueArray = byteValueArray;
    }

    public char[] getCharValueArray() {
        return charValueArray;
    }

    public void setCharValueArray(char[] charValueArray) {
        this.charValueArray = charValueArray;
    }

    public float[] getFloatValueArray() {
        return floatValueArray;
    }

    public void setFloatValueArray(float[] floatValueArray) {
        this.floatValueArray = floatValueArray;
    }

    public double[] getDoubleValueArray() {
        return doubleValueArray;
    }

    public void setDoubleValueArray(double[] doubleValueArray) {
        this.doubleValueArray = doubleValueArray;
    }

    public boolean[] getBooleanValueArray() {
        return booleanValueArray;
    }

    public void setBooleanValueArray(boolean[] booleanValueArray) {
        this.booleanValueArray = booleanValueArray;
    }

    public String[] getStringValueArray() {
        return stringValueArray;
    }

    public void setStringValueArray(String[] stringValueArray) {
        this.stringValueArray = stringValueArray;
    }

    public List<Long> getLongValues() {
        return longValues;
    }

    public void setLongValues(List<Long> longValues) {
        this.longValues = longValues;
    }

    public List<Integer> getIntValues() {
        return intValues;
    }

    public void setIntValues(List<Integer> intValues) {
        this.intValues = intValues;
    }

    public List<Short> getShortValues() {
        return shortValues;
    }

    public void setShortValues(List<Short> shortValues) {
        this.shortValues = shortValues;
    }

    public List<Byte> getByteValues() {
        return byteValues;
    }

    public void setByteValues(List<Byte> byteValues) {
        this.byteValues = byteValues;
    }

    public List<Character> getCharValues() {
        return charValues;
    }

    public void setCharValues(List<Character> charValues) {
        this.charValues = charValues;
    }

    public List<Float> getFloatValues() {
        return floatValues;
    }

    public void setFloatValues(List<Float> floatValues) {
        this.floatValues = floatValues;
    }

    public List<Double> getDoubleValues() {
        return doubleValues;
    }

    public void setDoubleValues(List<Double> doubleValues) {
        this.doubleValues = doubleValues;
    }

    public List<Boolean> getBooleanValues() {
        return booleanValues;
    }

    public void setBooleanValues(List<Boolean> booleanValues) {
        this.booleanValues = booleanValues;
    }

    public List<String> getStringValues() {
        return stringValues;
    }

    public void setStringValues(List<String> stringValues) {
        this.stringValues = stringValues;
    }

    public List<TestArrayModel> getObjectValues() {
        return objectValues;
    }

    public void setObjectValues(List<TestArrayModel> objectValues) {
        this.objectValues = objectValues;
    }

    public TestArrayModel[] getObjectValueArray() {
        return objectValueArray;
    }

    public void setObjectValueArray(TestArrayModel[] objectValueArray) {
        this.objectValueArray = objectValueArray;
    }

    public LocalDateTime[] getDateValueArray() {
        return dateValueArray;
    }

    public void setDateValueArray(LocalDateTime[] dateValueArray) {
        this.dateValueArray = dateValueArray;
    }

    public List<LocalDateTime> getDateValues() {
        return dateValues;
    }

    public void setDateValues(List<LocalDateTime> dateValues) {
        this.dateValues = dateValues;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TestArrayModel.class.getSimpleName() + "[", "]")
                .add("longValueArray=" + Arrays.toString(longValueArray))
                .add("intValueArray=" + Arrays.toString(intValueArray))
                .add("shortValueArray=" + Arrays.toString(shortValueArray))
                .add("byteValueArray=" + Arrays.toString(byteValueArray))
                .add("charValueArray=" + Arrays.toString(charValueArray))
                .add("floatValueArray=" + Arrays.toString(floatValueArray))
                .add("doubleValueArray=" + Arrays.toString(doubleValueArray))
                .add("booleanValueArray=" + Arrays.toString(booleanValueArray))
                .add("stringValueArray=" + Arrays.toString(stringValueArray))
                .add("objectValueArray=" + Arrays.toString(objectValueArray))
                .add("dateValueArray=" + Arrays.toString(dateValueArray))
                .add("longValues=" + longValues)
                .add("intValues=" + intValues)
                .add("shortValues=" + shortValues)
                .add("byteValues=" + byteValues)
                .add("charValues=" + charValues)
                .add("floatValues=" + floatValues)
                .add("doubleValues=" + doubleValues)
                .add("booleanValues=" + booleanValues)
                .add("stringValues=" + stringValues)
                .add("dateValues=" + dateValues)
                .add("objectValues=" + objectValues)
                .toString();
    }
}

