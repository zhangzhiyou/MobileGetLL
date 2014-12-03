package com.xiayule.getll.action;

import com.opensymphony.xwork2.Action;
import com.xiayule.getll.service.CreditLogService;
import com.xiayule.getll.service.CreditService;
import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.utils.DecimalUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by tan on 14-7-22.
 */
public class SearchAction implements Action {
    private SubscriberService subscriberService;
    private CreditService creditService;
//    private CreditLogService creditLogService;

    private String mobile;
    private String tip;

    @Override
    public String execute() throws Exception {
        // 如果订阅了服务
        if (subscriberService.isSubscribe(mobile)) {
            //获得 流量币计数，并格式化为一位小数
            String credit = DecimalUtils.formatDecial(creditService.getCredit(mobile));

            // 获得排名
            Long rank = creditService.getRankByTotalAtHere(mobile);

            // 取得日志
//            Map<String, List<String>> logs = creditLogService.readLog(mobile);


            HttpServletRequest request = ServletActionContext.getRequest();

            // 如果该值存在，证明是返回的结果
            request.setAttribute("ret", true);

            // 获得的数据
            request.setAttribute("credit", credit);
            request.setAttribute("rank", rank);
//            request.setAttribute("logs", logs);

            return SUCCESS;
        } else {
            tip = "该手机号未使用过本站服务!!!";
            return ERROR;
        }
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

    public void setCreditService(CreditService creditService) {
        this.creditService = creditService;
    }

//    public void setCreditLogService(CreditLogService creditLogService) {
//        this.creditLogService = creditLogService;
//    }
}
