package org.forkjoin.scrat.apikit.plugin.bean;

import org.forkjoin.scrat.apikit.tool.info.ClassInfo;

import java.util.ArrayList;
import java.util.List;

public abstract class Task {
    protected String version;
    private boolean clean;
    private List<String> cleanExcludes;
    private String nameMapperSource;
    private String nameMapperDist;
    private String outPath;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }



    protected List<ClassInfo> filterList = new ArrayList<>();

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public List<ClassInfo> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<ClassInfo> filterList) {
        this.filterList = filterList;
    }

    public boolean isClean() {
        return clean;
    }

    public void setClean(boolean clean) {
        this.clean = clean;
    }

    public List<String> getCleanExcludes() {
        return cleanExcludes;
    }

    public void setCleanExcludes(List<String> cleanExcludes) {
        this.cleanExcludes = cleanExcludes;
    }

    public String getNameMapperSource() {
        return nameMapperSource;
    }

    public void setNameMapperSource(String nameMapperSource) {
        this.nameMapperSource = nameMapperSource;
    }

    public String getNameMapperDist() {
        return nameMapperDist;
    }

    public void setNameMapperDist(String nameMapperDist) {
        this.nameMapperDist = nameMapperDist;
    }

    @Override
    public String toString() {
        return "Task{" +
                "version='" + version + '\'' +
                ", clean=" + clean +
                ", cleanExcludes=" + cleanExcludes +
                ", nameMapperSource='" + nameMapperSource + '\'' +
                ", nameMapperDist='" + nameMapperDist + '\'' +
                ", outPath='" + outPath + '\'' +
                ", filterList=" + filterList +
                '}';
    }
}
