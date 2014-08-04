package com.xiayule.getll.toolkit.scheduling.impl;

import com.xiayule.getll.service.PlayService;
import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.toolkit.scheduling.JobTask;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by tan on 14-7-22.
 */
public class JobTaskImpl implements JobTask {
    private static Logger logger = LogManager.getLogger(JobTask.class.getName());

    private PlayService playService;
    private SubscriberService subscriberService;

    public void doJob() {
        List<String> subs = subscriberService.getAllSubscriber();

        int cnt = 0;

        /**
         * 执行任务
         */
        for (String sub : subs) {

            logger.info("JobTask:" + "执行任务:" + "订阅者:" + sub + " 当前第" + (++cnt) + "个任务");
            System.out.println("JobTask:" + "执行任务:" + "订阅者:" + sub +  "当前第" + (cnt) + "个任务");

            playService.autoPlay(sub);
        }

        System.out.println("JobTask:" + "任务执行完毕");
        logger.info("JobTask:" + "任务执行完毕");
    }

    public void setPlayService(PlayService playService) {
        this.playService = playService;
    }

    public void setSubscriberService(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }
}
