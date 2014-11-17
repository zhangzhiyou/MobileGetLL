package com.xiayule.getll.action;

import com.opensymphony.xwork2.Action;
import com.xiayule.getll.service.SubscriberService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tan on 14-10-10.
 */
public class AdminAction implements Action {
    private SubscriberService subscriberService;

    public int countSubscribers() {
        return subscriberService.countNumbers();
    }

    @Override
    public String execute() throws Exception {
        int mSubscribCount = subscriberService.countNumbers();

        HttpServletRequest request = ServletActionContext.getRequest();

        int mSubscribForFriendCount = subscriberService.getAllSubscriberForFriend().size();

        int mSubscribAutoReceiveCount = subscriberService.getAllSubscriberAutoReceive().size();

        request.setAttribute("mSubscribCount", mSubscribCount);
        request.setAttribute("mSubscribForFriendCount", mSubscribForFriendCount);
        request.setAttribute("mSubscribAutoReceiveCount", mSubscribAutoReceiveCount);

        return Action.SUCCESS;
    }


    // set and get method

    public SubscriberService getSubscriberService() {
        return subscriberService;
    }

    public void setSubscriberService(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }
}
