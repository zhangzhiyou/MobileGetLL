package com.xiayule.getll.db.model;

import java.util.Date;

/**
 * Created by tan on 14-11-26.
 */
public class VersionHistory {

    private Integer id;

    /**
     * version 的版本号, 例如 v7.1.2
     */
    private String versionName;

    /**
     * version 的标题
     */
    private String title;

    /**
     * version 的内容
     */
    private String content;

    /**
     * version 的时间
     */
    private Date time;

    // set and get methods


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
