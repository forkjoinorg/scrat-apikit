package org.forkjoin.scrat.apikit.client;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.AbstractMap;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EncodeUtils {
//    public static List<Map.Entry<String, Object>> encode(String $parent, LinkedHashMap<String, BaseObj<Number>> objectMap) {
//        if (objectMap != null) {
//            objectMap.forEach(($k,$v)->{
//                objectMap.encode($parent + "objectMap.", $list);
//            });
//        }
//    }

    public static void encode(List<Map.Entry<String, Object>> $list, String $name, byte[] value) {
        if (value != null) {
            $list.add(new AbstractMap.SimpleImmutableEntry<>($name, value));
        }
    }

    public static void encode(List<Map.Entry<String, Object>> $list, String $name, boolean value) {
        $list.add(new AbstractMap.SimpleImmutableEntry<>($name, value));
    }

    public static void encode(List<Map.Entry<String, Object>> $list, String $name, byte value) {
        $list.add(new AbstractMap.SimpleImmutableEntry<>($name, value));
    }

    public static void encode(List<Map.Entry<String, Object>> $list, String $name, short value) {
        $list.add(new AbstractMap.SimpleImmutableEntry<>($name, value));
    }

    public static void encode(List<Map.Entry<String, Object>> $list, String $name, char value) {
        $list.add(new AbstractMap.SimpleImmutableEntry<>($name, value));
    }

    public static void encode(List<Map.Entry<String, Object>> $list, String $name, int value) {
        $list.add(new AbstractMap.SimpleImmutableEntry<>($name, value));
    }

    public static void encode(List<Map.Entry<String, Object>> $list, String $name, long value) {
        $list.add(new AbstractMap.SimpleImmutableEntry<>($name, value));
    }

    public static void encode(List<Map.Entry<String, Object>> $list, String $name, float value) {
        $list.add(new AbstractMap.SimpleImmutableEntry<>($name, value));
    }

    public static void encode(List<Map.Entry<String, Object>> $list, String $name, double value) {
        $list.add(new AbstractMap.SimpleImmutableEntry<>($name, value));
    }


    public static void encode(List<Map.Entry<String, Object>> $list, String $name, Boolean value) {
        $list.add(new AbstractMap.SimpleImmutableEntry<>($name, value));
    }

    public static void encode(List<Map.Entry<String, Object>> $list, String $name, Byte value) {
        $list.add(new AbstractMap.SimpleImmutableEntry<>($name, value));
    }

    public static void encode(List<Map.Entry<String, Object>> $list, String $name, Short value) {
        $list.add(new AbstractMap.SimpleImmutableEntry<>($name, value));
    }

    public static void encode(List<Map.Entry<String, Object>> $list, String $name, Character value) {
        $list.add(new AbstractMap.SimpleImmutableEntry<>($name, value));
    }

    public static void encode(List<Map.Entry<String, Object>> $list, String $name, Integer value) {
        $list.add(new AbstractMap.SimpleImmutableEntry<>($name, value));
    }

    public static void encode(List<Map.Entry<String, Object>> $list, String $name, Long value) {
        $list.add(new AbstractMap.SimpleImmutableEntry<>($name, value));
    }

    public static void encode(List<Map.Entry<String, Object>> $list, String $name, Float value) {
        $list.add(new AbstractMap.SimpleImmutableEntry<>($name, value));
    }

    public static void encode(List<Map.Entry<String, Object>> $list, String $name, Double value) {
        $list.add(new AbstractMap.SimpleImmutableEntry<>($name, value));
    }

    public static void encode(List<Map.Entry<String, Object>> $list, String $name, String value) {
        $list.add(new AbstractMap.SimpleImmutableEntry<>($name, value));
    }

    public static void encode(List<Map.Entry<String, Object>> $list, String $name, Date value) {
        $list.add(new AbstractMap.SimpleImmutableEntry<>($name, value));
    }

    public static void encode(List<Map.Entry<String, Object>> $list, String $name, Temporal value) {
        $list.add(new AbstractMap.SimpleImmutableEntry<>($name, value));
    }

    public static void encode(List<Map.Entry<String, Object>> $list, String $name, EncodeObject encodeObject) {
        encodeObject.encode($name, $list);
    }

    /**
     * 基本类型的数组和List可以作为单个值被处理,注意必须是基础类型
     */
    public static void encodeSingle(List<Map.Entry<String, Object>> $list, String $name, Object single) {
        if (single == null) {
            return;
        }
        $list.add(new AbstractMap.SimpleImmutableEntry<>($name, single));
    }

    public static void encode(List<Map.Entry<String, Object>> $list, String $name, Enum statusType) {
        $list.add(new AbstractMap.SimpleImmutableEntry<>($name, statusType));
    }


//        if (objectMap != null) {
//            objectMap.forEach(($k,$v)->{
//                objectMap.encode($parent + "objectMap.", $list);
//            });
//        }
//    }
}
