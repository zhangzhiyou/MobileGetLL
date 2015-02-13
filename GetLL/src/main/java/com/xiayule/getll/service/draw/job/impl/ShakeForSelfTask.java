package com.xiayule.getll.service.draw.job.impl;

import com.xiayule.getll.db.service.CreditLogService;
import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.service.draw.job.ScheduledTask;
import com.xiayule.getll.service.draw.job.ShakeTask;
import com.xiayule.getll.service.draw.api.PlayService;
import com.xiayule.getll.utils.CreditUtils;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by tan on 14-9-8.
 */
@Component
public class ShakeForSelfTask implements ShakeTask, ScheduledTask {

    private static Logger logger = Logger.getLogger(ShakeTask.class);

    @Autowired
    private PlayService playService;

    @Autowired
    private CreditLogService creditLogService;

    @Autowired
    private SubscriberService subscriberService;

    private static boolean isRunning = false;

//    @Autowired
//    private DrawRequest drawRequest;

    public void autoPlay(String mobile) {
        logger.info("ScheduledTask:" + "执行任务:" + "订阅者:" + mobile);

        // 如果未登录, 就退出
        if (!playService.isLogined(mobile)) {
            logger.info("ScheduledTask:" + mobile + " 未登录(第一次尝试)");

            try {
                Thread.sleep(ShakeTask.PLAY_LAZY);
            } catch (InterruptedException e) {

            }

            if (!playService.isLogined(mobile)) {
                logger.info("ScheduledTask:" + mobile + " 未登录(第二次尝试), 跳过任务");
                return ;
            }
        }

        // 累加每日奖励, 并接收返回结果
        double firstShakeGiveCredit = playService.addDrawScore(mobile);

        // 每日登录获得的流量币
        if (firstShakeGiveCredit > 0) {
            creditLogService.logLoginCredit(mobile, firstShakeGiveCredit);
        }

        // 获取剩余次数
        int remainTimes = playService.getRemainTimes(mobile);

        logger.info(mobile + " 还剩 " + remainTimes + " 次");

        if (remainTimes > 0) {
            int cnt = 0;

            try {

                do {

                    String winName = playService.draw(mobile);

                    logger.info(mobile + " 第" + (++cnt) + "次摇奖,获得奖励:"
                            + winName);

                    if (CreditUtils.hasCredit(winName)) {
                        double credit = CreditUtils.parseCredit(winName);
                        creditLogService.logShakeCredit(mobile, credit);
                    }

                    playService.addDrawScore(mobile);

                    remainTimes = Integer.parseInt(playService.queryScore(mobile).getString("times"));

                    try {
                        // 等待 3 秒，保险起见
                        Thread.sleep(ShakeTask.PLAY_LAZY);
                    } catch (InterruptedException e) {
                        logger.info(mobile + " Thread.sleep error");
                    }

                } while (remainTimes > 0);
            } catch (Exception e) {
                logger.info(mobile + " 摇奖过程出错, draw or addDrawScore return null");
                // 摇奖出现错误，退出
                return ;
            }
        }

        // 查询分数
        JSONObject queryScore = playService.queryScore(mobile);

        logger.info(mobile + " 总计: 连续登录:" + queryScore.getString("count_1") + "天"
                + " 今日总计:" + queryScore.getString("todayCredit")
                + " 当前流量币: " + queryScore.getString("credit"));
    }


//    @Scheduled(cron = "0 0 5 * * ?")
    @Scheduled(cron = "0 40 22 * * ?")
    public void taskStart() {
        if (!isRunning) {
            isRunning = true;

            List<String> subs = subscriberService.getAllSubscriber();

            int cnt = 0;

            for (String sub : subs) {

                cnt++;

                try {
                    autoPlay(sub);
                } catch (Exception e) {
                    logger.info("摇奖过程出错");
                }

            }

            logger.info("ScheduledTask: " + cnt + " 个任务执行完毕");

            isRunning = false;
        } else {
            logger.info("ScheduledTask:" + "任务已经开启，无需再开启");
        }
    }


    public void setSubscriberService(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    public void setPlayService(PlayService playService) {
        this.playService = playService;
    }

   /* public void setDrawRequest(DrawRequest drawRequest) {
        this.drawRequest = drawRequest;
    }
*/
    public void setCreditLogService(CreditLogService creditLogService) {
        this.creditLogService = creditLogService;
    }
}
