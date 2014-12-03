package com.xiayule.getll.service.draw.request.impl;

import com.xiayule.getll.service.draw.request.DrawRequest;
import com.xiayule.getll.service.draw.job.AutoPlayJob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by tan on 14-9-5.
 */
public class DrawRequestImpl implements DrawRequest{
    private static Logger logger = LogManager.getLogger(DrawRequest.class.getName());

    private LinkedBlockingDeque<String> queue;// 存放手机号的阻塞队列

    private AutoPlayJob autoPlayJob;

    public void init() {
        queue = new LinkedBlockingDeque<String>();

        // 开启一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {// 不停的取
                    // try 在里面，有异常 不会影响其它任务的执行
                    try {
                        String mobile = queue.take();

                        logger.info("获得新的任务: " + mobile);

                        autoPlayJob.autoPlay(mobile);

                    } catch (Exception e) {
                        logger.info("摇取时，发生了错误");
                    }
                }

            }
        }).start();
    }


    @Override
    public void addRequest(String mobile) {
        try {
            // 如果不存在于队列中，则加入到队列
            if (!queue.contains(mobile)) {
                queue.put(mobile);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private DrawRequestImpl() {

    }

    public void setAutoPlayJob(AutoPlayJob autoPlayJob) {
        this.autoPlayJob = autoPlayJob;
    }
}
