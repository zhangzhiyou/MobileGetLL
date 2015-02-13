package com.xiayule.getll.service.draw.api;

import com.xiayule.getll.db.service.CreditLogService;
import com.xiayule.getll.utils.JsonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by tan on 14-11-15.
 * 存放一些自己定义的 service
 */
@Component
public class GiftsReceiveService {
    private static Logger logger = Logger.getLogger(GiftsReceiveService.class);

    @Autowired
    private PlayService playService;

    @Autowired
    private CreditLogService creditLogService;

    /**
     * 查看是否有未领取的流量币
     * @param mobile
     * @return
     * @throws Exception
     */
    public boolean isHasFlowScoreTransferGifts(String mobile) throws Exception {
        String rs = playService.getTransferGiftsList(mobile, "count", "others", "2");

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
            logger.info(mobile + " 自动领取过程发生错误");

            throw e;
        }
    }

    /**
     * 接收所有的流量币
     */
    public void transferGiftsReceiveAll(String mobile) throws Exception {
        //数据如下
        //{"message":"ok","result":{"totalCredit":"0.20","list":[{"typeName":"朋友摇奖","msisdn":"18369905136","handselDate":"2014-11-15 01:07","type":"2","credit":"0.2","handselMsisdn":"18753363865","handselID":"15050534"}]},"status":"ok","class":"class com.aspire.portal.web.vo.JsonResult","code":""}

        try {
            // 获得需要领取的所有的id json
            String transferList = playService.getTransferGiftsList(mobile, "all", "others", "2");

            JSONObject transferListJsonObj = JsonUtils.stringToJson(transferList);

            String transferListStatus = transferListJsonObj.getString("status");

            if (!transferListStatus.equals("ok")) {// 错误出来
                logger.info(mobile + " transferGiftsReceiveAll: 发生错误, 返回:(" + transferList + ")");

                return;
            } else {
                JSONObject result = transferListJsonObj.getJSONObject("result");

                // 获得可以领取的流量币总数
                double totalCredit = result.getDouble("totalCredit");

                // 获得领取列表
                JSONArray listJsonArray = result.getJSONArray("list");

                // 获得领取列表迭代器
                ListIterator<JSONObject> listIterator = listJsonArray.listIterator();

                // 存放所有要领取的 id
                ArrayList<String> idArray = new ArrayList<String>();

                while (listIterator.hasNext()) {
                    JSONObject listItem = listIterator.next();

                    String handselID = listItem.getString("handselID");

                    idArray.add(handselID);
                }

                String ids = StringUtils.join(idArray, ",");

                playService.transferGiftsReceive(mobile, ids);

                // 执行到这里没有报错，就计入日志
                creditLogService.logReceiveCredit(mobile, totalCredit);
            }
        } catch (Exception e) {
            logger.info(mobile + " 自动领取过程发生错误");
            throw e;
        }
    }

    // set and get methods

    public void setPlayService(PlayService playService) {
        this.playService = playService;
    }
}
