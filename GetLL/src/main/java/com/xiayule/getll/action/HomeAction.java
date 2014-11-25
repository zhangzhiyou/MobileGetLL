package com.xiayule.getll.action;

import com.opensymphony.xwork2.Action;
import com.xiayule.getll.service.SubscriberService;

/**
 * Created by tan on 14-10-2.
 */
public class HomeAction implements Action{

    private SubscriberService subscriberService;

    @Override
    public String execute() throws Exception {
//        System.out.println("access HOme Action");

        

        return SUCCESS;
    }

    // set and get methods

    public void setSubscriberService(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }
}
