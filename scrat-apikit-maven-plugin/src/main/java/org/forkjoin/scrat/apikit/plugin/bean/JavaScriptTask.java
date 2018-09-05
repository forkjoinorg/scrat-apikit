package org.forkjoin.scrat.apikit.plugin.bean;

import org.forkjoin.scrat.apikit.tool.wrapper.JSWrapper;

public class JavaScriptTask extends Task{
    protected String version;
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

    @Override
    public String toString() {
        return "JavaScriptTask{" +
                "version='" + version + '\'' +
                ", type=" + type +
                '}';
    }
}
