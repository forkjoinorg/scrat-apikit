package org.forkjoin.scrat.apikit.plugin.bean;

public class JavaClientTask extends Task{

    private String rootPackage;



    public String getRootPackage() {
        return rootPackage;
    }

    public void setRootPackage(String rootPackage) {
        this.rootPackage = rootPackage;
    }

    @Override
    public String toString() {
        return "JavaClientTask{" +
                ", rootPackage='" + rootPackage + '\'' +
                '}';
    }
}
