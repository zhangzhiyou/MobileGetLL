package com.xiayule.getll;

import com.xiayule.getll.service.impl.PlayServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by tan on 14-7-20.
 */
public class Test {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        PlayServiceImpl playService = ctx.getBean("playService", PlayServiceImpl.class);

        playService.setMobile("18369905136");

//        System.out.println(playService.loginDo());
        System.out.println(playService.isLogined());
        // 如果未登录, 就登录
        if (!playService.isLogined()) {
//            playService.loginDo();
        }

        // 如果已经登录
        int remainTimes = playService.getRemainTimes();
//        for (int i=0; i<remainTimes; i++) {
//            System.out.println(playService.draw());
//            System.out.println(playService.addDrawScore());
//        }

        System.out.println(playService.queryScore());

    //    System.out.println(SerialNumberUtils.getSerialNumber());
    }
}
