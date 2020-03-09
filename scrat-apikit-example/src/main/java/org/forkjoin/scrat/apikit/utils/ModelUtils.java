package org.forkjoin.scrat.apikit.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.forkjoin.TestModel;
import org.forkjoin.scrat.apikit.example.model.ObjectModel;
import org.forkjoin.scrat.apikit.example.form.child.TestArrayModel;
import org.forkjoin.scrat.apikit.example.model.TestWrapperModel;

import java.util.Arrays;

public class ModelUtils {
    public static ObjectModel newObjectModel() {
        ObjectModel m = new ObjectModel();
        TestArrayModel testArray = new TestArrayModel();
        TestModel test = new TestModel();
        TestWrapperModel testWrapper = new TestWrapperModel();

//        m.setTestArray(testArray);
//        m.setTest(test);
//        m.setTestWrapper(testWrapper);


        testArray.setBooleanValueArray(new boolean[]{newBoolean(), newBoolean()});
        testArray.setBooleanValues(Arrays.asList(newBoolean(), newBoolean()));

        testArray.setByteValueArray(new byte[]{newByte(), newByte()});
        testArray.setByteValues(Arrays.asList(newByte(), newByte()));

        testArray.setShortValueArray(new short[]{newShort(), newShort()});
        testArray.setShortValues(Arrays.asList(newShort(), newShort()));


        testArray.setIntValueArray(new int[]{newInt(), newInt()});
        testArray.setIntValues(Arrays.asList(newInt(), newInt()));

        testArray.setLongValueArray(new long[]{newLong(), newLong()});
        testArray.setLongValues(Arrays.asList(newLong(), newLong()));

        testArray.setFloatValueArray(new float[]{newFloat(), newFloat()});
        testArray.setFloatValues(Arrays.asList(newFloat(), newFloat()));

        testArray.setDoubleValueArray(new double[]{newDouble(), newDouble()});
        testArray.setDoubleValues(Arrays.asList(newDouble(), newDouble()));

        testArray.setCharValueArray(new char[]{newChar(), newChar()});
        testArray.setCharValues(Arrays.asList(newChar(), newChar()));

        testArray.setStringValueArray(new String[]{newString(), newString()});
        testArray.setStringValues(Arrays.asList(newString(), newString()));


        test.setBooleanValue(newBoolean());
        test.setByteValue(newByte());
        test.setShortValue(newShort());
        test.setIntValue(newInt());
        test.setLongValue(newLong());
        test.setFloatValue(newFloat());
        test.setDoubleValue(newDouble());
        test.setStringValue(newString());
        test.setCharValue(newChar());


        testWrapper.setBooleanValue(newBoolean());
        testWrapper.setByteValue(newByte());
        testWrapper.setShortValue(newShort());
        testWrapper.setIntValue(newInt());
        testWrapper.setLongValue(newLong());
        testWrapper.setFloatValue(newFloat());
        testWrapper.setDoubleValue(newDouble());
        testWrapper.setStringValue(newString());
        testWrapper.setCharValue(newChar());

        return m;
    }

    private static String newString() {
        return RandomStringUtils.random(32);
    }

    private static boolean newBoolean() {
        return RandomUtils.nextBoolean();
    }

    private static byte newByte() {
        return (byte) RandomUtils.nextInt(0, Byte.MAX_VALUE);
    }

    private static short newShort() {
        return (byte) RandomUtils.nextInt(0, Short.MAX_VALUE);
    }

    private static int newInt() {
        return RandomUtils.nextInt();
    }

    private static char newChar() {
        return RandomStringUtils.random(1).charAt(0);
    }

    private static long newLong() {
        return RandomUtils.nextLong();
    }

    private static float newFloat() {
        return RandomUtils.nextFloat();
    }

    private static double newDouble() {
        return RandomUtils.nextDouble();
    }
}
