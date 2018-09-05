package org.forkjoin.scrat.apikit.plugin.bean;

public class GitTask extends Task{
//     gitGenerator.setGitUrl("https://code.aliyun.com/gxhl/mz_mall.git");
    //                    gitGenerator.setGitUser("gitApi");
//                    gitGenerator.setGetPassword("uxgdpkpy");
//                    gitGenerator.setGitEmail("wugang0005@dingtalk.com");
//                    gitGenerator.setGitName("gitApi");


    private String url;
    private String user;
    private String password;
    private String authorEmail;
    private String authorName;
    private Task task;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "GitTask{" +
                "url='" + url + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", authorEmail='" + authorEmail + '\'' +
                ", authorName='" + authorName + '\'' +
                '}';
    }
}
