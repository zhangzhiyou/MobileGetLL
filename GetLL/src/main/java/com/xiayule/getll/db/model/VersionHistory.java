package com.xiayule.getll.db.model;

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
    private String time;
}
