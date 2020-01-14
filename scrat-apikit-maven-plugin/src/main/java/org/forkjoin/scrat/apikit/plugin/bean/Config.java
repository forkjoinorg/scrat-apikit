package org.forkjoin.scrat.apikit.plugin.bean;

import org.apache.maven.plugins.annotations.Parameter;
import org.forkjoin.scrat.apikit.tool.info.ClassInfo;

import java.util.List;

public class Config {

    private GitInfo git;

    @Parameter
    private List<ClassInfo> javaFilterList;
    private List<ClassInfo> javaScriptFilterList;

    private Boolean clean;
    private List<String> cleanExcludes;
    private String nameMapperSource;
    private String nameMapperDist;


    public GitInfo getGit() {
        return git;
    }

    public void setGit(GitInfo git) {
        this.git = git;
    }

    public List<ClassInfo> getJavaFilterList() {
        return javaFilterList;
    }

    public void setJavaFilterList(List<ClassInfo> javaFilterList) {
        this.javaFilterList = javaFilterList;
    }

    public List<ClassInfo> getJavaScriptFilterList() {
        return javaScriptFilterList;
    }

    public void setJavaScriptFilterList(List<ClassInfo> javaScriptFilterList) {
        this.javaScriptFilterList = javaScriptFilterList;
    }

    public Boolean getClean() {
        return clean;
    }

    public void setClean(Boolean clean) {
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
        return "Config{" +
                "git=" + git +
                ", javaFilterList=" + javaFilterList +
                ", javaScriptFilterList=" + javaScriptFilterList +
                ", clean=" + clean +
                ", cleanExcludes=" + cleanExcludes +
                ", nameMapperSource='" + nameMapperSource + '\'' +
                ", nameMapperDist='" + nameMapperDist + '\'' +
                '}';
    }
}
