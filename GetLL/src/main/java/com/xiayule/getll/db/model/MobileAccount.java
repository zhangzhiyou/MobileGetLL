package com.xiayule.getll.db.model;

/**
 * 用户 Model
 * Created by tan on 14-10-26.
 */
public class MobileAccount {
    private Integer id;

    private String mobile;
    private String nick;

    private String email;

    // 是否自动领取
    private Boolean isAutoReceive;

    // 是否开启朋友摇奖
    private Boolean isForFriend;

//    todo: valid?

    // set and get methods

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsAutoReceive() {
        return isAutoReceive;
    }

    public void setIsAutoReceive(Boolean isAutoReceive) {
        this.isAutoReceive = isAutoReceive;
    }

    public Boolean getIsForFriend() {
        return isForFriend;
    }

    public void setIsForFriend(Boolean isForFriend) {
        this.isForFriend = isForFriend;
    }
}
