package org.forkjoin.scrat.apikit.tool.generator;

public class DefaultMessagePackageNameMapper implements MessagePackageNameMapper {
    @Override
    public String apply(String sourceRootPackage, String packageName) {
        boolean isInPack = packageName.startsWith(sourceRootPackage);
        if (isInPack) {
            String dist = packageName.substring(sourceRootPackage.length());
            if(dist.startsWith(".")){
                return dist.substring(1);
            }
            return dist;
        }
        return "core";
    }
}
