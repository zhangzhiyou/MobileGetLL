package com.xiayule.getll.draw.impl;

import com.xiayule.getll.draw.DrawRequest;
import com.xiayule.getll.service.AutoPlayService;
import com.xiayule.getll.service.PlayService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by tan on 14-9-5.
 */
public class DrawRequestImpl implements DrawRequest{
    private static Logger logger = LogManager.getLogger(DrawRequest.class.getName());

    private LinkedBlockingDeque<String> queue;// 存放手机号的阻塞队列

    private AutoPlayService autoPlayService;

    public void init() {
        queue = new LinkedBlockingDeque<String>();

        // 开启一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {// 不挺的取
                    // try 在里面，有异常 不会影响其它任务的执行
                    try {
                        String mobile = queue.take();

                        logger.info("获得新的任务: " + mobile);

                        autoPlayService.autoPlay(mobile);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
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

    public void setAutoPlayService(AutoPlayService autoPlayService) {
        this.autoPlayService = autoPlayService;
    }
}
