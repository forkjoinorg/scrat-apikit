package org.forkjoin.scrat.apikit.tool.generator;

import java.util.Set;

public interface MessageNameMapper {
    String apply(Set<String> nameSet, String packageName, String name);
}
