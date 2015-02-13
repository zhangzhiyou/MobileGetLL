package com.xiayule.getll.db.model;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Created by tan on 15-2-10.
 */
public class Function {

    private Integer id;

    private String mobile;

    private Boolean autoReceive;

    private Boolean forFriend;

    public Function() {
        autoReceive = false;
        forFriend = false;
    }

    public Function(String mobile) {
        autoReceive = false;
        forFriend = false;

        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "id:" + id + " mobile:" + mobile
                + " forFriend:" + forFriend
                + " autoReceive:" + autoReceive;
    }

    // set and get methods


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAutoReceive() {
        return autoReceive;
    }

    public void setAutoReceive(Boolean autoReceive) {
        this.autoReceive = autoReceive;
    }

    public Boolean getForFriend() {
        return forFriend;
    }

    public void setForFriend(Boolean forFriend) {
        this.forFriend = forFriend;
    }
}
