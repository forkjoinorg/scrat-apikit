package org.forkjoin.scrat.apikit.plugin.bean;

import org.forkjoin.scrat.apikit.tool.wrapper.JSWrapper;

public class JavaScriptTask extends Task{
    protected String version;
    protected String jsPackageName;
    private JSWrapper.Type type = JSWrapper.Type.CommonJS;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public JSWrapper.Type getType() {
        return type;
    }

    public void setType(JSWrapper.Type type) {
        this.type = type;
    }

    public String getJsPackageName() {
        return jsPackageName;
    }

    public void setJsPackageName(String jsPackageName) {
        this.jsPackageName = jsPackageName;
    }

    @Override
    public String toString() {
        return "JavaScriptTask{" +
                "version='" + version + '\'' +
                ", jsPackageName='" + jsPackageName + '\'' +
                ", type=" + type +
                '}';
    }
}
