package org.forkjoin.scrat.apikit.tool.info;

import org.forkjoin.scrat.apikit.tool.Utils;

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


    public String getWriteName() {

        return "set" + Utils.toClassName(getName());
    }

    public String getReadName() {
        if (getTypeInfo().getType().equals(TypeInfo.Type.BOOLEAN)) {
            return "is" + Utils.toClassName(getName());
        }
        return "get" + Utils.toClassName(getName());
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
