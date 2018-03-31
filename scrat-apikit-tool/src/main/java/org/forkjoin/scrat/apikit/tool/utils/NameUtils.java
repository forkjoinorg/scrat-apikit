package org.forkjoin.scrat.apikit.tool.utils;

/**
 *
 */
public class NameUtils {
    public static final String toFieldName(String name){
        if(!name.isEmpty()){
            char c = name.charAt(0);
            return Character.toLowerCase(c) + name.substring(1);
        }
        return name;
    }
}
