package org.forkjoin.scrat.apikit.plugin.bean;

import java.util.List;

public class GitInfo{
    /**
     * 对应git  user.name
     */
    private String name;
    /**
     * 对应git  user.email
     */
    private String email;

    private String url;
    private String branch = "master";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
