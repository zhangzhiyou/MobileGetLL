package com.xiayule.getll.db.model;

/**
 * 用户 Model
 * Created by tan on 14-10-26.
 */
public class User {
    private String mobile;
    private String nick;

    private String email;

    // 是否自动领取
    private Boolean isAutoReceive;

    // 是否开启朋友摇奖
    private Boolean isForFriend;
}
