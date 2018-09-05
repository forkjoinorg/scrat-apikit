package org.forkjoin.scrat.apikit.plugin.bean;

public abstract class Task {
    private String outPath;

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    @Override
    public String toString() {
        return "Task{" +
                "outPath='" + outPath + '\'' +
                '}';
    }
}
