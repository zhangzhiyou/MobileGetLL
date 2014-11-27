package com.xiayule.getll.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.xiayule.getll.service.SubscriberService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Created by tan on 14-10-10.
 */
public class AdminAction {
    private SubscriberService subscriberService;

    private String password;

    private String title;
    private String content;
    private String versionName;
    private Date time;

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

        if (password != null && password.equals("123")) {
            Map session = ActionContext.getContext().getSession();
            session.put("admin", true);
        } else {
            return Action.LOGIN;
        }

        return Action.SUCCESS;
    }

    public String newHistoryVersion() {
//        todo: time 没有传过来

        System.out.println("title:" + title
                + " contnt:" + content
                + " versionName:" + versionName
                + " time:" + time);

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
