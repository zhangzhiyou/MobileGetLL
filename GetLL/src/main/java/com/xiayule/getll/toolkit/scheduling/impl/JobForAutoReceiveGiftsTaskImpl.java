package com.xiayule.getll.toolkit.scheduling.impl;

import com.xiayule.getll.draw.job.AutoPlayJob;
import com.xiayule.getll.draw.job.impl.AutoReceiveJob;
import com.xiayule.getll.service.PlayService;
import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.toolkit.scheduling.JobTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;

/**
 * Created by tan on 14-11-16.
 */
public class JobForAutoReceiveGiftsTaskImpl implements JobTask {
    private static Logger logger = LogManager.getLogger(JobTask.class.getName());


    private AutoPlayJob autoPlayJob;
    private SubscriberService subscriberService;
    private PlayService playService;

    private static boolean isRunning = false;

    @Override
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
                    autoPlayJob.autoPlay(sub);
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

    // set and get methods


    public void setAutoPlayJob(AutoPlayJob autoPlayJob) {
        this.autoPlayJob = autoPlayJob;
    }

    public void setSubscriberService(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    public void setPlayService(PlayService playService) {
        this.playService = playService;
    }
}
