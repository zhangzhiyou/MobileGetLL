package com.xiayule.getll.action;

import com.opensymphony.xwork2.Action;
import com.sun.glass.ui.mac.MacPasteboard;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.xiayule.getll.service.PlayService;
import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.utils.UserUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 处理主页的Action
 * 进入到这里
 * 1. 不能保证有mobile Cookie, 拦截器藐视只能拦截 action 请求, todo: 以后考虑增加过滤器
 * 2. 有效期未到期,因为登录时,如果发现有效期到期,会自动续期.
 * Created by tan on 14-10-2.
 */
public class HomeAction implements Action{

    //todo: 日志
    private static Logger logger = LogManager.getLogger(HomeAction.class.getName());

    private SubscriberService subscriberService;
    private PlayService playService;

    @Override
    public String execute() throws Exception {
//        System.out.println("access HOme Action");

        String mobile = UserUtils.getMobileFromCookie();

//        todo:
        if (mobile == null) {
            System.out.println("需要重新登录");

            return LOGIN;
        } /*ed -lse if (!playService.isLogined(mobile)) {// 这个还是在js里面检测

        }
*/

        // 取得 request
        HttpServletRequest request = ServletActionContext.getRequest();

        // 创建模型
        Map<String, Object> model = new HashMap<String, Object>();

        // 取得 user-agent, 判断是否为手机用户
        String userAgent = request.getHeader("User-Agent");
        Boolean isMobile = UserUtils.isMobile(userAgent);
        model.put("isMobile", isMobile);

        logger.info("useragent:" + userAgent);


        // 查询有效期
        String ttl = subscriberService.getTTLDays(mobile);

        // 将参数封装到 model
        model.put("ttl", ttl);

        // 传递参数到jsp
        request.setAttribute("model", model);

        return SUCCESS;
    }

    // set and get methods

    public void setSubscriberService(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    public void setPlayService(PlayService playService) {
        this.playService = playService;
    }
}
