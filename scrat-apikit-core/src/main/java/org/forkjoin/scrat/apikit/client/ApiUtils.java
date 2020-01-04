package org.forkjoin.scrat.apikit.client;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * api 父类，定义了几个通用方法
 *
 * @author zuoge85 on 15/6/16.
 */
public class ApiUtils {
    private static final Pattern NAMES_PATTERN = Pattern.compile("\\{([^/]+?)\\}");

    public static String expandUriComponent(String url, Map<String, ?> uriVariables) {
        if (url == null) {
            return null;
        }
        if (url.indexOf('{') == -1) {
            return url;
        }
        Matcher matcher = NAMES_PATTERN.matcher(url);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String match = matcher.group(1);
            String variableName = getVariableName(match);
            Object variableValue = uriVariables.get(variableName);
            if (variableValue == null) {
                throw new RuntimeException(MessageFormat.format("协议定义错误，需要参数[{0}]未找到,[url:{1},uriVariables:{2}]", variableName, url, uriVariables));
            }
            String variableValueString = getVariableValueAsString(variableValue);

            String replacement = null;
            try {
                replacement = Matcher.quoteReplacement(URLEncoder.encode(variableValueString, "utf8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    private static String getVariableName(String match) {
        int colonIdx = match.indexOf(':');
        return (colonIdx != -1 ? match.substring(0, colonIdx) : match);
    }

    private static String getVariableValueAsString(Object variableValue) {
        if (variableValue != null) {
            if (variableValue instanceof List) {
                List list = (List) variableValue;
                StringBuilder sb = new StringBuilder();
                for (Object item : list) {
                    if (sb.length() > 0) {
                        sb.append(',');
                    }
                    sb.append(item);
                }
                return sb.toString();
            }
            return variableValue.toString();
        } else {
            return "";
        }
    }

    public static ApiType type(Type raw, Type... types) {
        return new ApiType(raw, types);
    }

}
