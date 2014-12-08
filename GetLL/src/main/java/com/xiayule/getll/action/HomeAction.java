package com.xiayule.getll.action;

import com.opensymphony.xwork2.Action;
import com.xiayule.getll.db.service.CreditLogService;
import com.xiayule.getll.domain.CreditRank;
import com.xiayule.getll.service.draw.api.PlayService;
import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.utils.DecimalUtils;
import com.xiayule.getll.utils.UserUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理主页的Action
 * 进入到这里
 * 1. 不能保证有mobile Cookie, 拦截器藐视只能拦截 action 请求, todo: 以后考虑增加过滤器
 * 2. 有效期未到期,因为登录时,如果发现有效期到期,会自动续期.
 * Created by tan on 14-10-2.
 */
@Component
@Scope("prototype")
public class HomeAction implements Action{

    //todo: 日志
    private static Logger logger = LogManager.getLogger(HomeAction.class.getName());

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private PlayService playService;

    @Autowired
    private CreditLogService creditLogService;

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
        model.put("ttl", ttl);

        // 查询排名
        CreditRank firstRank = creditLogService.queryYestodayFirstRank();

        CreditRank mobileRank = creditLogService.queryYestoDayRank(mobile);

        Integer count = creditLogService.queryYesterdayMobileCount();

        String beatPercent = DecimalUtils.formatPersont((double)mobileRank.getRank()/count);

        model.put("firstRank", firstRank);
        model.put("mobileRank", mobileRank);
        model.put("beatPercent", beatPercent);

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

    public void setCreditLogService(CreditLogService creditLogService) {
        this.creditLogService = creditLogService;
    }
}
