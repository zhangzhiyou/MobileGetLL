package com.xiayule.getll.action;

import com.opensymphony.xwork2.Action;

/**
 * 用于指向首页（为了解决 sae 的默认首页不能自动加载问题)
 * Created by tan on 14-7-12.
 */
public class DefaultAction implements Action {
    @Override
    public String execute() throws Exception {
//        System.out.println("access defaultACtion");
        return SUCCESS;
    }
}
