package org.forkjoin.scrat.apikit.plugin.bean;

import java.util.List;

public class Group {

    private List<Task> tasks;

    private String rootPackage;

    public Group() {
    }

    public Group(List<Task> tasks, String rootPackage) {
        this.tasks = tasks;
        this.rootPackage = rootPackage;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getRootPackage() {
        return rootPackage;
    }

    public void setRootPackage(String rootPackage) {
        this.rootPackage = rootPackage;
    }

    @Override
    public String toString() {
        return "Group{" +
                "tasks=" + tasks +
                ", rootPackage='" + rootPackage + '\'' +
                '}';
    }
}
