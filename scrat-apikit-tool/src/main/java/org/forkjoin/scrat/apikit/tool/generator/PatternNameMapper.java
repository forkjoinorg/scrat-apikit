package org.forkjoin.scrat.apikit.tool.generator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternNameMapper implements NameMapper {

    /**
     * A RegExp Pattern that extract needed information from a service ID. Ex :
     * "(?<name>.*)-(?<version>v.*$)"
     */
    private Pattern sourcePattern;
    /**
     * A RegExp that refer to named groups define in servicePattern. Ex :
     * "${version}/${name}"
     */
    private String distPattern;


    public PatternNameMapper(String sourcePattern, String distPattern) {
        this.sourcePattern = Pattern.compile(sourcePattern);
        this.distPattern = distPattern;
    }

    /**
     *
     */
    public String apply(String name) {
        Matcher matcher = this.sourcePattern.matcher(name);
        String distName = matcher.replaceFirst(this.distPattern);
        distName = clean(distName);
        return (hasText(distName) ? distName : name);
    }

    private String clean(final String route) {
        String routeToClean = route.replaceAll("/{2,}", "/");
        if (routeToClean.startsWith("/")) {
            routeToClean = routeToClean.substring(1);
        }
        if (routeToClean.endsWith("/")) {
            routeToClean = routeToClean.substring(0, routeToClean.length() - 1);
        }
        return routeToClean;
    }

    public static boolean hasLength(CharSequence str) {
        return str != null && str.length() > 0;
    }

    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        } else {
            int strLen = str.length();

            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean hasText(String str) {
        return hasText((CharSequence) str);
    }
}
