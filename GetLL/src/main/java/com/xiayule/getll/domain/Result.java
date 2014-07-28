package com.xiayule.getll.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by tan on 14-7-27.
 */
public class Result {
    boolean status;
    List list;
    Map mp;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Map getMp() {
        return mp;
    }

    public void setMp(Map mp) {
        this.mp = mp;
    }
}
