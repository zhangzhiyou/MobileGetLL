package com.xiayule.getll;

import com.xiayule.getll.action.AdminAction;
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
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        PlayService playService = ctx.getBean("playService", PlayService.class);

        String cookieMobile = "18369905136";
        String friendMobile = "18369905506";

        System.out.println(playService.get(cookieMobile, "http://shake.sd.chinamobile.com/"));
        System.out.println(playService.loadLoginMobile(cookieMobile));
        System.out.println(playService.queryScore(cookieMobile));
        System.out.println(playService.setDrawMobile(cookieMobile, friendMobile));
        System.out.println(playService.drawWithSource(cookieMobile));
        System.out.println(playService.addDrawScore(cookieMobile));

    }
}
