package com.xiayule.getll.draw.job.impl;

import com.xiayule.getll.draw.job.AutoPlayJob;
import com.xiayule.getll.service.CreditService;
import com.xiayule.getll.service.PlayService;
import com.xiayule.getll.utils.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by tan on 14-10-26.
 */
public class AutoPlayForFriendJob implements AutoPlayJob {

    private static Logger logger = LogManager.getLogger(AutoPlayJob.class.getName());

    private PlayService playService;
    private CreditService creditService;

    /**
     * 为朋友摇奖，自己的手机号永远都是固定的。
     * @param friendMobile 朋友的手机号
     */
    public void autoPlay(String friendMobile) {

        final String myMobile = "15069890845";

        if (myMobile == friendMobile) return;

        //todo: addDrawScore 是可以的
        //todo: 但是 load 和draw 不可以
        //todo: 浏览器中的 cookie 设置都是正确的

        playService.addDrawScoreWithSource(myMobile);

        try {

            if (playService.isLogined(myMobile)) {
                logger.info(myMobile + " 为（" + friendMobile + ")摇奖 setDrawMobile返回(" + playService.setDrawMobile(myMobile, friendMobile) + ")");

                Thread.sleep(3000);

                // 累加每日奖励, 并接收返回结果
                double firstShakeGiveCredit = playService.addDrawScore(myMobile);

                Thread.sleep(3000);

                // 获取剩余次数
                int drawCount = playService.getRemainTimes(myMobile);

                Thread.sleep(3000);

                int cnt = 0;

                while (drawCount > 0) {
                    String drawResult = playService.drawWithSource(myMobile);

                    logger.info(myMobile + "为(" + friendMobile + ")第(" + ++cnt + ")次摇奖 draw 返回(" + drawResult + ")");

                    //todo: 这里有可能出错
                    String strDrawCount = JsonUtils.stringToJson(drawResult).getJSONObject("result").getString("drawCount");

                    drawCount = Integer.parseInt(strDrawCount);

                    logger.info(myMobile + "为(" + friendMobile + ")摇奖 addDrawScore 返回(" + playService.addDrawScoreWithSource(myMobile) + ")");


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
            logger.info(myMobile + " 为朋友(" + friendMobile + ")摇取过程出错");
        } finally {// 无论是否出错，执行完毕后都要设置回自己的手机号
            logger.info(myMobile + " 为朋友(" + friendMobile + ")摇取完毕, 设置自己的手机号, 返回(" + playService.setDrawMobile(myMobile, myMobile) + ")");
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        AutoPlayForFriendJob.logger = logger;
    }

    public PlayService getPlayService() {
        return playService;
    }

    public void setPlayService(PlayService playService) {
        this.playService = playService;
    }

    public CreditService getCreditService() {
        return creditService;
    }

    public void setCreditService(CreditService creditService) {
        this.creditService = creditService;
    }
}
