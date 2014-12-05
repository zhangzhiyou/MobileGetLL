package com.xiayule.getll.toolkit.scheduling.impl;

import com.xiayule.getll.service.draw.request.DrawRequest;
import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.service.draw.job.JobTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by tan on 14-7-22.
 */
public class JobTaskImpl implements JobTask {
    private static Logger logger = LogManager.getLogger(JobTask.class.getName());

    private DrawRequest drawRequest;

//    private PlayService playService;
    private SubscriberService subscriberService;

    /**
     * quartz 本身就会将任务开启多次,
     * 而　spring　配置文件又会加载 ２次
     * 因此会导致多次执行
     * 增加下面配置只能使得 quartz 任务开启一次
     * 不能保证　多次被加载任务开启多次
     */
    private static boolean isRunning = false;

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

    public void setDrawRequest(DrawRequest drawRequest) {
        this.drawRequest = drawRequest;
    }
}
