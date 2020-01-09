package org.forkjoin.scrat.apikit.plugin.bean;

import org.forkjoin.scrat.apikit.tool.info.ClassInfo;

import java.util.ArrayList;
import java.util.List;

public abstract class Task {
    private String outPath;
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

    @Override
    public String toString() {
        return "Task{" +
                "outPath='" + outPath + '\'' +
                ", filterList=" + filterList +
                '}';
    }
}
