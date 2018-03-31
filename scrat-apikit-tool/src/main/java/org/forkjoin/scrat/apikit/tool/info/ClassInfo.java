package org.forkjoin.scrat.apikit.tool.info;

import java.util.Objects;

public class ClassInfo {
    private String packageName;
    private String name;

    public ClassInfo() {
    }

    public ClassInfo(String packageName, String name) {
        this.packageName = packageName;
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return packageName + "." + name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassInfo classInfo = (ClassInfo) o;
        return Objects.equals(packageName, classInfo.packageName) &&
                Objects.equals(name, classInfo.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(packageName, name);
    }

    @Override
    public String toString() {
        return "ClassInfo{" +
                "packageName='" + packageName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
