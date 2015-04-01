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
    private String groupIndex;

    public MobileGroup() {

    }

    public MobileGroup(String mobile) {
        this(mobile, UUIDUtils.generate());
        this.id = 1;
    }

    public MobileGroup(String mobile, String index) {
        this.mobile = mobile;
        this.groupIndex = index;
    }

    @Override
    public String toString() {
        return "id:" + id + " mobile:" + mobile + " groupIndex:" + groupIndex;
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

    public String getGroupIndex() {
        return groupIndex;
    }

    public void setGroupIndex(String groupIndex) {
        this.groupIndex = groupIndex;
    }
}
