package com.xiayule.getll.toolkit.scheduling.impl;

import com.xiayule.getll.service.PlayService;
import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.toolkit.scheduling.JobTask;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by tan on 14-7-22.
 */
public class JobTaskImpl implements JobTask {
    private PlayService playService;
    private SubscriberService subscriberService;

    public void doJob() {
        List<String> subs = subscriberService.getAllSubscriber();

        /**
         * 执行任务
         */
        for (String sub : subs) {
            System.out.println("JobTask:" + "执行任务:" + "订阅者:" + sub);

            playService.autoPlay(sub);
        }

        System.out.println("JobTask:" + "任务执行完毕");
    }

    public void setPlayService(PlayService playService) {
        this.playService = playService;
    }

    public void setSubscriberService(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }
}
