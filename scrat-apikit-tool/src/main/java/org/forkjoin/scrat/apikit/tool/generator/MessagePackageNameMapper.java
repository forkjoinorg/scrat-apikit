package org.forkjoin.scrat.apikit.tool.generator;

public interface MessagePackageNameMapper {
    String apply(String sourceRootPackage, String packageName);
}
