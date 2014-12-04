package com.xiayule.getll.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.xiayule.getll.service.SubscriberService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Created by tan on 14-10-10.
 */
@Component
@Scope("prototype")
public class AdminAction {
    @Autowired
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

    /**
     * todo:没有显示信息
     * @return
     */
    public String adminLogin() {

        if (password != null && password.equals("123")) {
            Map session = ActionContext.getContext().getSession();
            session.put("admin", true);
        } else {
            return Action.LOGIN;
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
