package com.xiayule.getll.draw.impl;

import com.xiayule.getll.draw.DrawRequest;
import com.xiayule.getll.service.AutoPlayService;
import com.xiayule.getll.service.PlayService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by tan on 14-9-5.
 */
public class DrawRequestImpl implements DrawRequest{
    private LinkedBlockingDeque<String> queue;// 存放手机号的阻塞队列

    private AutoPlayService autoPlayService;

    public void init() {
        queue = new LinkedBlockingDeque<String>();

        // 开启一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {// 不挺的取
                        String mobile = queue.take();

                        autoPlayService.autoPlay(mobile);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void addRequest(String mobile) {
        try {
            queue.put(mobile);
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
