package com.xiayule.getll.service.draw.job.impl;

import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.service.draw.api.GiftsReceiveService;
import com.xiayule.getll.service.draw.api.PlayService;
import com.xiayule.getll.service.draw.job.ShakeTask;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by tan on 14-11-16.
 * 自动领取流量币任务
 */
@Component
public class ReceiveGiftsTask implements Runnable {

    private static Logger logger = Logger.getLogger(ShakeTask.class);

    @Autowired
    private GiftsReceiveService giftsReceiveService;

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private PlayService playService;

    @Autowired
    private TaskExecutor taskExecutor;

    private static boolean isRunning = false;

    public void autoPlay(String mobile) {
        try {
            if (giftsReceiveService.isHasFlowScoreTransferGifts(mobile)){

                Thread.sleep(ShakeTask.PLAY_LAZY);

                giftsReceiveService.transferGiftsReceiveAll(mobile);

                Thread.sleep(ShakeTask.PLAY_LAZY);
            }
        } catch (Exception e) {
            logger.info(mobile + " AutoReceiveJob: 出错");
        }
    }

    public void taskStart() {
        logger.info("JobForAutoReceiveGiftsTaskImpl 自动领取任务开始");

        if (!isRunning) {

            isRunning = true;

            // 获取所有订阅下午摇奖的人
            List<String> subs = subscriberService.getAllSubscriberAutoReceive();

            int cnt = 0;

            for (String sub : subs) {
                // 如果有效期到期或者登录不成功, 则不执行
                if (/*!subscriberService.isSubscribe(sub) && */!playService.isLogined(sub)) continue;

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

    @Override
    public void run() {
        taskStart();
    }

    @Scheduled(cron = "0 0 21 * * ?")
    public void startExecute() {
        taskExecutor.execute(this);
    }


    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        ReceiveGiftsTask.logger = logger;
    }

    public void setGiftsReceiveService(GiftsReceiveService giftsReceiveService) {
        this.giftsReceiveService = giftsReceiveService;
    }
}
