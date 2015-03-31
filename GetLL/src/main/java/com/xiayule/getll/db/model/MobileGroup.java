package com.xiayule.getll.db.model;

import com.xiayule.getll.utils.UUIDUtils;

/**
 * Created by tan on 15-3-31.
 *
 */
public class MobileGroup {
    private Integer id;
    private String mobile;

    /**
     * 相同owner的手机号 index 相同, 这里使用 uuid
     */
    private String index;

    public MobileGroup() {

    }

    public MobileGroup(String mobile) {
        this(mobile, UUIDUtils.generate());
    }

    public MobileGroup(String mobile, String index) {
        this.mobile = mobile;
        this.index = index;
    }

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

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
