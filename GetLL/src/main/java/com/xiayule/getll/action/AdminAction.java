package com.xiayule.getll.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.service.draw.job.impl.ShakeForSelfTask;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

    private Map json;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private ShakeForSelfTask shakeForSelfTask;


    public Long countSubscribers() {
        return subscriberService.countNumbers();
    }

    public String admin() throws Exception {
        Long mSubscribCount = subscriberService.countNumbers();

        HttpServletRequest request = ServletActionContext.getRequest();

        int mSubscribForFriendCount = subscriberService.getAllSubscriberForFriend().size();

        int mSubscribAutoReceiveCount = subscriberService.getAllSubscriberAutoReceive().size();

        request.setAttribute("mSubscribCount", mSubscribCount);
        request.setAttribute("mSubscribForFriendCount", mSubscribForFriendCount);
        request.setAttribute("mSubscribAutoReceiveCount", mSubscribAutoReceiveCount);

        // 线程池信息
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("CurrentPoolSize : ").append(executor.getPoolSize());
        strBuff.append(" - CorePoolSize : ").append(executor.getCorePoolSize());
        strBuff.append(" - ActiveTaskCount : ").append(executor.getActiveCount());

        request.setAttribute("executorInfo", strBuff.toString());

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

    static int count = 1;

    /**
     * 开启为自己摇奖任务
     * @return
     */
    public String startForSelfTask() {
        json = new HashMap();

        if (shakeForSelfTask.isRunning()) {
            json.put("status", "error");
            json.put("msg", "摇奖任务正在执行");
        } else {
            json.put("status", "ok");
            shakeForSelfTask.startExecute();
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

    public Map getJson() {
        return json;
    }

    public void setJson(Map json) {
        this.json = json;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
