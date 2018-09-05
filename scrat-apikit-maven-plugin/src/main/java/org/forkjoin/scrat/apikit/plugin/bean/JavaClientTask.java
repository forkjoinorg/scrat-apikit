package org.forkjoin.scrat.apikit.plugin.bean;

public class JavaClientTask extends Task{
    private String nameMaperSource;

    private String nameMaperDist;

    private String rootPackage;


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

    public String getRootPackage() {
        return rootPackage;
    }

    public void setRootPackage(String rootPackage) {
        this.rootPackage = rootPackage;
    }

    @Override
    public String toString() {
        return "JavaClientTask{" +
                "nameMaperSource='" + nameMaperSource + '\'' +
                ", nameMaperDist='" + nameMaperDist + '\'' +
                ", rootPackage='" + rootPackage + '\'' +
                '}';
    }
}
