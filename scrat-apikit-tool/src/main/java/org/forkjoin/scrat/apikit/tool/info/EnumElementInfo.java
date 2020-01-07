package org.forkjoin.scrat.apikit.tool.info;

public class EnumElementInfo {

    private String name;
    private int ordinal;
    private JavadocInfo javadocInfo;

    public EnumElementInfo() {
    }

    public EnumElementInfo(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
    }

    public EnumElementInfo(String name, int ordinal, JavadocInfo javadocInfo) {
        this.name = name;
        this.ordinal = ordinal;
        this.javadocInfo = javadocInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }


    public JavadocInfo getJavadocInfo() {
        return javadocInfo;
    }

    public void setJavadocInfo(JavadocInfo javadocInfo) {
        this.javadocInfo = javadocInfo;
    }

    @Override
    public String toString() {
        return "EnumElementInfo{" +
                "name='" + name + '\'' +
                ", ordinal=" + ordinal +
                ", javadocInfo=" + javadocInfo +
                '}';
    }
}
