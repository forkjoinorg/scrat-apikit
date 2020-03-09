package org.forkjoin.scrat.apikit.example.form;

import java.util.Date;
import java.util.StringJoiner;

public class TestWrapperForm {
    private Long longValue;
    private Integer intValue;
    private Short shortValue;
    private Byte byteValue;
    private Character charValue;
    private Float floatValue;
    private Double doubleValue;
    private Boolean booleanValue;
    private String stringValue;
    private Date dateValue;

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public Integer getIntValue() {
        return intValue;
    }

    public void setIntValue(Integer intValue) {
        this.intValue = intValue;
    }

    public Short getShortValue() {
        return shortValue;
    }

    public void setShortValue(Short shortValue) {
        this.shortValue = shortValue;
    }

    public Byte getByteValue() {
        return byteValue;
    }

    public void setByteValue(Byte byteValue) {
        this.byteValue = byteValue;
    }

    public Character getCharValue() {
        return charValue;
    }

    public void setCharValue(Character charValue) {
        this.charValue = charValue;
    }

    public Float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(Float floatValue) {
        this.floatValue = floatValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TestWrapperForm.class.getSimpleName() + "[", "]")
                .add("longValue=" + longValue)
                .add("intValue=" + intValue)
                .add("shortValue=" + shortValue)
                .add("byteValue=" + byteValue)
                .add("charValue=" + charValue)
                .add("floatValue=" + floatValue)
                .add("doubleValue=" + doubleValue)
                .add("booleanValue=" + booleanValue)
                .add("stringValue='" + stringValue + "'")
                .add("dateValue=" + dateValue)
                .toString();
    }
}

