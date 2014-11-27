package com.xiayule.getll.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.xiayule.getll.service.SubscriberService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by tan on 14-10-10.
 */
public class AdminAction {
    private SubscriberService subscriberService;

    private String password;

    public int countSubscribers() {
        return subscriberService.countNumbers();
    }

    public String admin() throws Exception {
        int mSubscribCount = subscriberService.countNumbers();

        HttpServletRequest request = ServletActionContext.getRequest();

        int mSubscribForFriendCount = subscriberService.getAllSubscriberForFriend().size();

        int mSubscribAutoReceiveCount = subscriberService.getAllSubscriberAutoReceive().size();

        request.setAttribute("mSubscribCount", mSubscribCount);
        request.setAttribute("mSubscribForFriendCount", mSubscribForFriendCount);
        request.setAttribute("mSubscribAutoReceiveCount", mSubscribAutoReceiveCount);

        return Action.SUCCESS;
    }

    public String adminLogin() {

        if (password != null && password.equals("6224989a")) {

            Map session = ActionContext.getContext().getSession();
            session.put("admin", true);

        }

        return Action.SUCCESS;
    }

    // set and get method

    public SubscriberService getSubscriberService() {
        return subscriberService;
    }

    public void setSubscriberService(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
