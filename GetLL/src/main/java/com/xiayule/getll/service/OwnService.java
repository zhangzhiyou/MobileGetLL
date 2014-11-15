package com.xiayule.getll.service;

import com.xiayule.getll.utils.JsonUtils;
import net.sf.json.JSONObject;

/**
 * Created by tan on 14-11-15.
 * 存放一些自己定义的 service
 */
public class OwnService {
    /**
     * 自己定义的，用来查询是否有未领取的流量币
     * 和 getFlowScoreTransferGiftsInfo 基本类似
     */
    /*FlowScore.prototype.isHasFlowScoreTransferGifts = function() {
        $.post("/ajax/getTransferGiftsList.action?queryType=count&type=others&status=2&r=" + Math.random(), {}, function (data) {
            if (data.status != "ok") {
                return false;
            } else {
                var result = data.result;
                if (Number(result.totalCredit) > 0) {
                    return true;
                } else return false;
            }
        }, "json");
    }*/

    private PlayService playService;

    /**
     * 查看是否有未领取的流量币
     * @param cookieMobile
     * @return
     * @throws Exception
     */
    public boolean isHasFlowScoreTransferGifts(String cookieMobile) throws Exception {
        String rs = playService.getTransferGiftsList(cookieMobile, "count", "others", "2");

        System.out.println(rs);

        try {
            JSONObject jsonObj = JsonUtils.stringToJson(rs);

            String status = jsonObj.getString("status");
            if (!status.equals("ok")) {
                return false;
            } else {
                Double totalCredit = jsonObj.getJSONObject("result").getDouble("totalCredit");
                if (totalCredit > 0) {
                    return true;
                } else {
                    return false;
                }
            }

        } catch (Exception e) { // 网络错误或者是一些其他的错误
            throw e;
        }
    }



    // set and get methods

    public void setPlayService(PlayService playService) {
        this.playService = playService;
    }
}
