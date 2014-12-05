package com.xiayule.getll.service.draw.job.impl;

import com.xiayule.getll.db.service.CreditLogService;
import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.service.draw.job.AutoPlayJob;
import com.xiayule.getll.service.PlayService;
import com.xiayule.getll.service.draw.request.DrawRequest;
import com.xiayule.getll.service.draw.job.JobTask;
import com.xiayule.getll.utils.CreditUtils;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by tan on 14-9-8.
 */
@Component
public class AutoPlayJobImpl implements AutoPlayJob, JobTask {

    private static Logger logger = LogManager.getLogger(AutoPlayJob.class.getName());

    @Autowired
    private PlayService playService;

    @Autowired
    private CreditLogService creditLogService;

    @Autowired
    private SubscriberService subscriberService;

    private static boolean isRunning = false;

    @Autowired
    private DrawRequest drawRequest;

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

                if (CreditUtils.hasCredit(winName)) {
                    double credit = CreditUtils.parseCredit(winName);
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


    @Scheduled(cron = "0 0 5 * * ?")
    @Override
    public void doJob() {
        if (!isRunning) {
            isRunning = true;

            List<String> subs = subscriberService.getAllSubscriber();

            int cnt = 0;

            for (String sub : subs) {

                cnt++;

                drawRequest.addRequest(sub);
            }

            logger.info("JobTask:" + "将 " + cnt + " 个任务加入队列");

            isRunning = false;
        } else {
            logger.info("Jobtask:" + "任务已经开启，无需再开启");
        }
    }


    public void setSubscriberService(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    public void setPlayService(PlayService playService) {
        this.playService = playService;
    }

    public void setDrawRequest(DrawRequest drawRequest) {
        this.drawRequest = drawRequest;
    }

    public void setCreditLogService(CreditLogService creditLogService) {
        this.creditLogService = creditLogService;
    }
}
