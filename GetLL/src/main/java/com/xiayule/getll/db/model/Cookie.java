package com.xiayule.getll.db.model;

/**
 * Created by tan on 15-2-9.
 */
public class Cookie {

    public static final String JSESSIONID = "JSESSIONID";
    public static final String LOGINTOKEN = "loginToken_encrypt";
    public static final String NICKNAME= "nickName_encrypt";
    public static final String PROVWAP = "prov_wap_encrypt";
    public static final String USERSIGN = "userSign_wap_encrypt";

    private Integer id;

    private String mobile;

    private String jSessionId;
    private String loginToken;
    private String nickName;
    private String provWap;
    private String userSignWap;

    public void update(Cookie cookie) {
        this.jSessionId = cookie.getjSessionId();
        this.loginToken = cookie.getLoginToken();
        this.nickName = cookie.getNickName();
        this.provWap = cookie.getProvWap();
        this.userSignWap = cookie.getUserSignWap();
    }

    // set and get methods

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getjSessionId() {
        return jSessionId;
    }

    public void setjSessionId(String jSessionId) {
        this.jSessionId = jSessionId;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProvWap() {
        return provWap;
    }

    public void setProvWap(String privWap) {
        this.provWap = privWap;
    }

    public String getUserSignWap() {
        return userSignWap;
    }

    public void setUserSignWap(String userSignWap) {
        this.userSignWap = userSignWap;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
