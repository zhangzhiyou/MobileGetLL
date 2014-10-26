package com.xiayule.getll.toolkit.scheduling.impl;

import com.xiayule.getll.draw.job.AutoPlayJob;
import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.toolkit.scheduling.JobTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

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

    @Override
    public void doJob() {
        logger.info("开始为朋友摇奖");

        List<String> subs = subscriberService.getAllSubscriber();

        int cnt = 0;

        for (String sub : subs) {
            cnt++;

            autoPlayJob.autoPlay(sub);
        }

        logger.info("JobTask:" + "将 " + cnt + " 个任务加入队列");
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
