package org.forkjoin.scrat.apikit.example;

import org.forkjoin.scrat.apikit.example.model.ObjectModel;

import java.util.Arrays;
import java.util.List;

public class TestArrayForm {
    private ObjectModel objectModel;
    private long[] longValueArray;
    private int[] intValueArray;
    private short[] shortValueArray;
    private byte[] byteValueArray;
    private char[] charValueArray;
    private float[] floatValueArray;
    private double[] doubleValueArray;
    private boolean[] booleanValueArray;
    private String[] stringValueArray;

    private List<Long> longValues;
    private List<Integer> intValues;
    private List<Short> shortValues;
    private List<Byte> byteValues;
    private List<Character> charValues;
    private List<Float> floatValues;
    private List<Double> doubleValues;
    private List<Boolean> booleanValues;
    private List<String> stringValues;

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

    public ObjectModel getObjectModel() {
        return objectModel;
    }

    public void setObjectModel(ObjectModel objectModel) {
        this.objectModel = objectModel;
    }

    @Override
    public String toString() {
        return "TestArrayForm{" +
                "objectModel=" + objectModel +
                ", longValueArray=" + Arrays.toString(longValueArray) +
                ", intValueArray=" + Arrays.toString(intValueArray) +
                ", shortValueArray=" + Arrays.toString(shortValueArray) +
                ", byteValueArray=" + Arrays.toString(byteValueArray) +
                ", charValueArray=" + Arrays.toString(charValueArray) +
                ", floatValueArray=" + Arrays.toString(floatValueArray) +
                ", doubleValueArray=" + Arrays.toString(doubleValueArray) +
                ", booleanValueArray=" + Arrays.toString(booleanValueArray) +
                ", stringValueArray=" + Arrays.toString(stringValueArray) +
                ", longValues=" + longValues +
                ", intValues=" + intValues +
                ", shortValues=" + shortValues +
                ", byteValues=" + byteValues +
                ", charValues=" + charValues +
                ", floatValues=" + floatValues +
                ", doubleValues=" + doubleValues +
                ", booleanValues=" + booleanValues +
                ", stringValues=" + stringValues +
                '}';
    }
}

