package com.xiayule.getll.action;

import com.opensymphony.xwork2.Action;
import com.xiayule.getll.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tan on 14-7-22.
 */
@Component
@Scope("prototype")
public class SubscribeAction implements Action {
    @Autowired
    private SubscriberService subscriberService;

    private String mobile;
    private String tip;

    @Override
    public String execute() throws Exception {
        if (mobile.equals("")) {

            return ERROR;
        }

        // TODO:需要序列号
        if (subscriberService.subscribe(mobile, "try")) {
            return SUCCESS;
        } else {
            tip = "请输入有效的序列号";
            return ERROR;
        }
    }

    // set and get methods


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
