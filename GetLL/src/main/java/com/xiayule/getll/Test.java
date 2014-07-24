package com.xiayule.getll;

import com.xiayule.getll.service.CreditLogService;
import com.xiayule.getll.service.PlayService;
import com.xiayule.getll.service.impl.PlayServiceImpl;
import org.apache.log4j.Logger;
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


      /*  // 日志测试
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        CreditLogService creditLogService = ctx.getBean("creditLogService", CreditLogService.class);

        creditLogService.log("18369905136", "日志记录开始");
        creditLogService.log("18369905136", "日志记录1");

        Map<String, List<String>> rs = creditLogService.readLog("18369905136");

        Set<String> keys = rs.keySet();

        for (String key : keys) {
            System.out.println(key + ":");
            List<String> list = rs.get(key);
            for (String s : list) {
                System.out.println(s);
            }
        }
*/
        /*// 流量币计数 测试
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        CreditService creditService = ctx.getBean("creditService", CreditService.class);

        creditService.addCredit("18369905136", 0.1d);
        creditService.addCredit("18369905135", 0.2d);

        creditService.addCredit("18369905136", 0.2d);

        System.out.println(creditService.getCredit("18369905136"));
        System.out.println(creditService.getCredit("18369905135"));

        System.out.println(creditService.getRank("18369905136"));*/


        logger.info("ha");
        logger.debug("wa");
    }
    private static Logger logger = Logger.getLogger(PlayService.class);

}
