package com.xiayule.getll;

import com.xiayule.getll.service.CreditLogService;
import com.xiayule.getll.service.impl.PlayServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tan on 14-7-20.
 */
public class Test {
    public static void main(String[] args) {
      /*  ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
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

    //    System.out.println(SerialNumberUtils.getSerialNumber());*/


        // 日志测试
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        CreditLogService creditLogService = ctx.getBean("creditLogService", CreditLogService.class);

        creditLogService.writeLog("110", "开始服务");
        creditLogService.writeLog("110", "开始了");

        Map<String, List<String>> rs = creditLogService.readLog("110");

        Set<String> keys = rs.keySet();

        for (String key : keys) {
            System.out.println(key + ":");
            List<String> list = rs.get(key);
            for (String s : list) {
                System.out.println(s);
            }
        }
    }
}
