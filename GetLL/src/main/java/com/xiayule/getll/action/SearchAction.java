package com.xiayule.getll.action;

import com.opensymphony.xwork2.Action;
import com.xiayule.getll.service.SubscriberService;

/**
 * Created by tan on 14-7-22.
 */
public class SearchAction implements Action {
    private SubscriberService subscriberService;

    private String mobile;
    private String tip;

    @Override
    public String execute() throws Exception {

        return SUCCESS;
    }

    // get and set methods


    public void setSubscriberService(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
