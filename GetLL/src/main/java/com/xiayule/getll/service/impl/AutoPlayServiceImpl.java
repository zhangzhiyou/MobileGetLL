package com.xiayule.getll.service.impl;

import com.xiayule.getll.service.AutoPlayService;
import com.xiayule.getll.service.CreditService;
import com.xiayule.getll.service.PlayService;
import com.xiayule.getll.utils.JsonUtils;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by tan on 14-9-8.
 */
public class AutoPlayServiceImpl implements AutoPlayService {

    private static Logger logger = LogManager.getLogger(AutoPlayService.class.getName());

    private PlayService playService;
    private CreditService creditService;

    @Override
    public void autoPlayForSelf(String mobile) {
        logger.info("JobTask:" + "执行任务:" + "订阅者:" + mobile);

        // 如果未登录, 就退出
        if (!playService.isLogined(mobile)) {
            logger.info("JobTask:" + mobile + " 未登录，跳过该任务");
            return ;
        }

        // 累加每日奖励, 并接收返回结果
        double firstShakeGiveCredit = playService.addDrawScore(mobile);

        // 流量币计数, 在本站获取的流量币总数
//        if (firstShakeGiveCredit > 0) {
//            creditService.addCredit(mobile, firstShakeGiveCredit);
//        }

        // 获取剩余次数
        int remainTimes = playService.getRemainTimes(mobile);

        logger.info(mobile + " 还剩 " + remainTimes + " 次");
//        creditLogService.log(mobile, "还剩 " + remainTimes + " 次");

        if (remainTimes > 0) {
            int cnt = 0;

            do {
                String winName = playService.draw(mobile);

//                creditLogService.log(mobile, "第" + (++cnt) + "次摇奖,获得奖励:"
//                        + winName);
                logger.info(mobile + " 第" + (++cnt) + "次摇奖,获得奖励:"
                        + winName);

                // 如果获得的流量币，就要 计数
                // 解析出来获得的流量币
                // 如果获得流量币，一般都是 '0.1个流量币' 这样的形式
                if (winName.contains("个流量币")) {
                    double credit = Double.parseDouble(winName.replace("个流量币", ""));
                    creditService.addCredit(mobile, credit);
                }

                playService.addDrawScore(mobile);

                remainTimes = Integer.parseInt(playService.queryScore(mobile).getString("times"));

                try {
                    // 等待 3 秒，保险起见
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    logger.info(mobile + " Thread.sleep error");
                }

//                creditLogService.log(mobile, " 剩余次数:" + remainTimes);
//                logger.info(mobile + " 剩余次数:" + remainTimes);
            } while (remainTimes > 0);
        }

        // 总结性的日志，要放在 list 的最前面
        // 查询分数
        JSONObject queryScore = playService.queryScore(mobile);

//        creditService.setDayCredit(mobile, queryScore.getDouble("todayCredit"));

//        creditLogService.log(mobile, "总计: 连续登录:" + queryScore.getString("count_1") + "天"
//                + " 今日总计:" + queryScore.getString("todayCredit")
//                + " 当前流量币: " + queryScore.getString("credit"));

        logger.info(mobile + " 总计: 连续登录:" + queryScore.getString("count_1") + "天"
                + " 今日总计:" + queryScore.getString("todayCredit")
                + " 当前流量币: " + queryScore.getString("credit"));
    }

    @Override
    public void autoPlayForFriend(String myMobile, String friendMobile) {

        if (myMobile == friendMobile) return;

        //todo: addDrawScore 是可以的
        //todo: 但是 load 和draw 不可以
        //todo: 浏览器中的 cookie 设置都是正确的

        playService.addDrawScoreWithSource(myMobile);

        try {

            if (playService.isLogined(myMobile)) {
                logger.info(playService.setDrawMobile(myMobile, friendMobile));

                // 累加每日奖励, 并接收返回结果
                double firstShakeGiveCredit = playService.addDrawScore(myMobile);

                // 获取剩余次数
                int drawCount = playService.getRemainTimes(myMobile);

                while (drawCount > 0) {
                    String drawResult = playService.drawWithSource(myMobile);

                    System.out.println(myMobile + " draw 返回(" + drawResult + ")");

                    //todo: 这里有可能出错
                    String strDrawCount = JsonUtils.stringToJson(drawResult).getJSONObject("result").getString("drawCount");

                    drawCount = Integer.parseInt(strDrawCount);

                    System.out.println(myMobile + " addDrawScore 返回(" + playService.addDrawScoreWithSource(myMobile) + ")");


                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        logger.info(myMobile + "为朋友摇奖(" + friendMobile + ")" + "Thread.sleep error");
                    }
                }
            } else {
                System.out.println(myMobile + " 没有登录, 无法为" + friendMobile + "摇奖");
            }
        } catch (Exception e) {
            // 设置回自己的 手机号
            logger.info(myMobile + " 为朋友" + friendMobile + "摇取过程出错");
        } finally {// 无论是否出错，执行完毕后都要设置回自己的手机号
            logger.info(myMobile + " 为朋友摇取完毕, 设置自己的手机号, 返回(" + playService.setDrawMobile(myMobile, myMobile) + ")");
        }
    }

    public void setPlayService(PlayService playService) {
        this.playService = playService;
    }

    public void setCreditService(CreditService creditService) {
        this.creditService = creditService;
    }
}
