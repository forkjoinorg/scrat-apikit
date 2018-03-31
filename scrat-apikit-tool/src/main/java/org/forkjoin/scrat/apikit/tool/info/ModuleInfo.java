package org.forkjoin.scrat.apikit.tool.info;

import org.apache.commons.lang3.StringUtils;
import org.forkjoin.scrat.apikit.tool.utils.NameUtils;

/**
 * @author zuoge85 on 15/6/11.
 */
public class ModuleInfo {
    private JavadocInfo comment;
    private String name;
    private String packageName;

    public void init(String name, String packageName, JavadocInfo comment) {
        this.name = name;
        this.packageName = packageName;
        this.comment = comment;
    }

    public JavadocInfo getComment() {
        return comment;
    }

    public void setComment(JavadocInfo comment) {
        this.comment = comment;
    }


    public String getName() {
        return name;
    }

    public String getFieldName() {
        return NameUtils.toFieldName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getFullName() {
        if (StringUtils.isEmpty(packageName)) {
            return name;
        }
        return packageName + "." + name;
    }
}
