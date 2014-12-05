package com.xiayule.getll.service.draw.job.impl;

import com.xiayule.getll.service.PlayService;
import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.service.draw.job.AutoPlayJob;
import com.xiayule.getll.service.OwnService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by tan on 14-11-16.
 * 自动领取流量币任务
 */
@Component
public class AutoReceiveJob implements AutoPlayJob {

    private static Logger logger = LogManager.getLogger(AutoPlayJob.class.getName());

    @Autowired
    private OwnService ownService;

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private PlayService playService;

    private static boolean isRunning = false;

    @Override
    public void autoPlay(String mobile) {
        try {
            if (ownService.isHasFlowScoreTransferGifts(mobile)){

                Thread.sleep(AutoPlayJob.PLAY_LAZY);

                ownService.transferGiftsReceiveAll(mobile);

                Thread.sleep(AutoPlayJob.PLAY_LAZY);
            }
        } catch (Exception e) {
            logger.info(mobile + " AutoReceiveJob: 出错");
        }
    }


//    @Scheduled(cron = "0 0 21 * * ?")
    @Scheduled(cron = "0 48 20 * * ?")
    public void doJob() {
        logger.info("JobForAutoReceiveGiftsTaskImpl 自动领取任务开始");

        if (!isRunning) {

            isRunning = true;

            // 获取所有订阅下午摇奖的人
            Set<String> subs = subscriberService.getAllSubscriberAutoReceive();

            int cnt = 0;

            for (String sub : subs) {
                // 如果有效期到期或者登录不成功, 则不执行
                if (!subscriberService.isSubscribe(sub) && !playService.isLogined(sub)) continue;

                cnt++;

                try {
                    autoPlay(sub);
                } catch (Exception e) {
                    logger.info(sub + " JobForAutoReceiveGiftsTaskImpl:" + "领取过程发生错误");
                }
            }

            logger.info("JobForAutoReceiveGiftsTaskImpl:" + "将 " + cnt + " 个任务加入队列");

            isRunning = false;

        } else {
            logger.info("JobForAutoReceiveGiftsTaskImpl:" + "任务已经开启，无需再开启");
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        AutoReceiveJob.logger = logger;
    }

    public void setOwnService(OwnService ownService) {
        this.ownService = ownService;
    }
}
