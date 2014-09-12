package com.xiayule.getll;

import com.xiayule.getll.service.PlayService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tan on 14-7-20.
 */
public class Test {
    public static void main(String[] args) {
        /*ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        PlayService playService = ctx.getBean("playService", PlayService.class);

        String mobile = "18369905136";

        System.out.println(playService.queryScore(mobile));

        playService.getRemainTimes(mobile);*/

    }
}


class Run implements Runnable {
    private static int c = 0;

    @Override
    public void run() {
        System.out.println(c++);
    }
}