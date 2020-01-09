package org.forkjoin.scrat.apikit.tool.info;

public class EnumElementInfo {

    private String name;
    private int ordinal;
    private JavadocInfo comment;

    public EnumElementInfo() {
    }

    public EnumElementInfo(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
    }

    public EnumElementInfo(String name, int ordinal, JavadocInfo comment) {
        this.name = name;
        this.ordinal = ordinal;
        this.comment = comment;
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


    public JavadocInfo getComment() {
        return comment;
    }

    public void setComment(JavadocInfo comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "EnumElementInfo{" +
                "name='" + name + '\'' +
                ", ordinal=" + ordinal +
                ", javadocInfo=" + comment +
                '}';
    }
}
