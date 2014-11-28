package com.xiayule.getll.draw.job.impl;

import com.xiayule.getll.draw.job.AutoPlayJob;
import com.xiayule.getll.service.CreditService;
import com.xiayule.getll.service.PlayService;
import com.xiayule.getll.utils.JsonUtils;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by tan on 14-9-8.
 */
public class AutoPlayJobImpl implements AutoPlayJob {

    private static Logger logger = LogManager.getLogger(AutoPlayJob.class.getName());

    private PlayService playService;
    private CreditService creditService;

    @Override
    public void autoPlay(String mobile) {
        logger.info("JobTask:" + "执行任务:" + "订阅者:" + mobile);

        // 如果未登录, 就退出
        if (!playService.isLogined(mobile)) {
            logger.info("JobTask:" + mobile + " 未登录(第一次尝试)");

            try {
                Thread.sleep(AutoPlayJob.PLAY_LAZY);
            } catch (InterruptedException e) {

            }

            if (!playService.isLogined(mobile)) {
                logger.info("JobTask:" + mobile + " 未登录(第二次尝试), 跳过任务");

                return ;
            }
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
                    Thread.sleep(AutoPlayJob.PLAY_LAZY);
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

    public void setPlayService(PlayService playService) {
        this.playService = playService;
    }

    public void setCreditService(CreditService creditService) {
        this.creditService = creditService;
    }
}
