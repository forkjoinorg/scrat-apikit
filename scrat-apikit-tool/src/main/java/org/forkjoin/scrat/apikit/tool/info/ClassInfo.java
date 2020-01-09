package org.forkjoin.scrat.apikit.tool.info;

import org.springframework.http.codec.multipart.FilePart;

import java.util.Objects;

public class ClassInfo implements Comparable<ClassInfo> {
    private String packageName;
    private String name;
    private String fullName;

    public ClassInfo() {
    }

    public ClassInfo(TypeInfo typeInfo) {
        this(typeInfo.getPackageName(), typeInfo.getName());
    }

    public ClassInfo(String packageName, String name) {
        this.packageName = packageName;
        this.name = name;
        this.fullName = packageName + "." + name;
    }

    public ClassInfo(Class<?> cls) {
        this.packageName = cls.getPackage().getName();
        this.name = cls.getSimpleName();
        this.fullName = cls.getName();
    }

    public String getPackageName() {
        return packageName;
    }


    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassInfo classInfo = (ClassInfo) o;
        return Objects.equals(packageName, classInfo.packageName) &&
                Objects.equals(name, classInfo.name);
    }


    public boolean equals(String packageName, String name) {
        return Objects.equals(packageName, this.packageName) &&
                Objects.equals(name, this.name);
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

    @Override
    public int compareTo(ClassInfo o) {
        int i = this.packageName.compareTo(o.packageName);
        if (i == 0) {
            return this.name.compareTo(o.name);
        } else {
            return i;
        }
    }
}
