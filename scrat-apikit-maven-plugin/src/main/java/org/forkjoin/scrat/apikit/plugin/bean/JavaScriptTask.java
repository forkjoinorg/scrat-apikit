package org.forkjoin.scrat.apikit.plugin.bean;

import org.forkjoin.scrat.apikit.tool.wrapper.JSWrapper;

public class JavaScriptTask extends Task{
    protected String version;
    protected String jsPackageName;
    private JSWrapper.Type type = JSWrapper.Type.CommonJS;
    private String nameMaperSource;

    private String nameMaperDist;

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


    public String getNameMaperSource() {
        return nameMaperSource;
    }

    public void setNameMaperSource(String nameMaperSource) {
        this.nameMaperSource = nameMaperSource;
    }

    public String getNameMaperDist() {
        return nameMaperDist;
    }

    public void setNameMaperDist(String nameMaperDist) {
        this.nameMaperDist = nameMaperDist;
    }

    @Override
    public String toString() {
        return "JavaScriptTask{" +
                "version='" + version + '\'' +
                ", jsPackageName='" + jsPackageName + '\'' +
                ", type=" + type +
                ", nameMaperSource='" + nameMaperSource + '\'' +
                ", nameMaperDist='" + nameMaperDist + '\'' +
                '}';
    }
}
