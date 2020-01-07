package org.forkjoin.scrat.apikit.tool.info;

/**
 * @author zuoge85 on 15/12/9.
 */
public class PropertyInfo extends FieldInfo {
    private JavadocInfo comment;
    private boolean isSuperProperty;

    public PropertyInfo(String name, TypeInfo typeInfo, boolean isSuperProperty) {
        super(name, typeInfo);
        this.isSuperProperty = isSuperProperty;
    }

    public boolean isSuperProperty() {
        return isSuperProperty;
    }

    public JavadocInfo getComment() {
        return comment;
    }

    public void setComment(JavadocInfo comment) {
        this.comment = comment;
    }
}
