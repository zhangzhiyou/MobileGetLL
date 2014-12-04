package com.xiayule.getll.service.draw.job.impl;

import com.xiayule.getll.db.service.CreditLogService;
import com.xiayule.getll.service.draw.job.AutoPlayJob;
import com.xiayule.getll.service.PlayService;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by tan on 14-9-8.
 */
public class AutoPlayJobImpl implements AutoPlayJob {

    private static Logger logger = LogManager.getLogger(AutoPlayJob.class.getName());

    private PlayService playService;
//    private CreditService creditService;

    private CreditLogService creditLogService;

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

        // 每日登录获得的流量币
        if (firstShakeGiveCredit > 0) {
//            creditLogService.log(mobile, firstShakeGiveCredit, CreditLog.LOG_LOGIN);
            creditLogService.logLoginCredit(mobile, firstShakeGiveCredit);
        }

        // 获取剩余次数
        int remainTimes = playService.getRemainTimes(mobile);

        logger.info(mobile + " 还剩 " + remainTimes + " 次");

        if (remainTimes > 0) {
            int cnt = 0;

            do {
                String winName = playService.draw(mobile);

                logger.info(mobile + " 第" + (++cnt) + "次摇奖,获得奖励:"
                        + winName);

                // 如果获得的流量币，就要 计数
                // 解析出来获得的流量币
                // 如果获得流量币，一般都是 '0.1个流量币' 这样的形式
                if (winName.contains("个流量币")) {
                    double credit = Double.parseDouble(winName.replace("个流量币", ""));

                    creditLogService.logShakeCredit(mobile, credit);
                }

                playService.addDrawScore(mobile);

                remainTimes = Integer.parseInt(playService.queryScore(mobile).getString("times"));

                try {
                    // 等待 3 秒，保险起见
                    Thread.sleep(AutoPlayJob.PLAY_LAZY);
                } catch (InterruptedException e) {
                    logger.info(mobile + " Thread.sleep error");
                }

            } while (remainTimes > 0);
        }

        // 查询分数
        JSONObject queryScore = playService.queryScore(mobile);

        logger.info(mobile + " 总计: 连续登录:" + queryScore.getString("count_1") + "天"
                + " 今日总计:" + queryScore.getString("todayCredit")
                + " 当前流量币: " + queryScore.getString("credit"));
    }

    public void setPlayService(PlayService playService) {
        this.playService = playService;
    }

    public void setCreditLogService(CreditLogService creditLogService) {
        this.creditLogService = creditLogService;
    }
}
