package com.xiayule.getll.toolkit.scheduling.impl;

import com.xiayule.getll.draw.job.AutoPlayJob;
import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.toolkit.scheduling.JobTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;

/**
 * Created by tan on 14-10-26.
 */
public class JobForFriendTaskImpl implements JobTask {

    private static Logger logger = LogManager.getLogger(JobTask.class.getName());

    private SubscriberService subscriberService;

    /**
     * 该 autoPlay 需要传入的额是 AutoPlayForFriend
     */
    private AutoPlayJob autoPlayJob;

    /**
     * quartz 本身就会将任务开启多次,
     * 而　spring　配置文件又会加载 ２次
     * 因此会导致多次执行
     * 增加下面配置只能使得 quartz 任务开启一次
     * 不能保证　多次被加载任务开启多次
     */
    private static boolean isRunning = false;

    @Override
    public void doJob() {
        logger.info("JobForFriendTaskImpl:开始为朋友摇奖");

        if (!isRunning) {

            isRunning = true;

            // 获取所有订阅下午摇奖的人
            Set<String> subs = subscriberService.getAllSubscriberForFriend();

            int cnt = 0;

            for (String sub : subs) {
                // 如果有效期到期, 则不执行
                if (!subscriberService.isSubscribe(sub)) continue;

                cnt++;

                try {
                    autoPlayJob.autoPlay(sub);
                } catch (Exception e) {
                    logger.info("JobForFriendTaskImpl: 为(" + sub + ")朋友摇奖发生错误");
                }
            }

            logger.info("JobForFriendTaskImpl:" + "将 " + cnt + " 个任务加入队列");

            isRunning = false;

        } else {
            logger.info("JobForFriendTaskImpl:" + "任务已经开启，无需再开启");
        }
    }

    public SubscriberService getSubscriberService() {
        return subscriberService;
    }

    public void setSubscriberService(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    public AutoPlayJob getAutoPlayJob() {
        return autoPlayJob;
    }

    public void setAutoPlayJob(AutoPlayJob autoPlayJob) {
        this.autoPlayJob = autoPlayJob;
    }
}
