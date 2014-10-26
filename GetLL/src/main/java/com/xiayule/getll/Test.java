package com.xiayule.getll;

import com.xiayule.getll.action.AdminAction;
import com.xiayule.getll.service.PlayService;
import com.xiayule.getll.utils.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tan on 14-7-20.
 */
public class Test {
    public static void main(String[] args) {


        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        PlayService playService = ctx.getBean("playService", PlayService.class);

        for (int i=0; i<30; i++) {
            System.out.println(playService.loadLoginMobile("18369905136"));
        }

        /*String cookieMobile = "18369905136";
        String friendMobile = "15069890845";

        //todo: addDrawScore 是可以的
        //todo: 但是 load 和draw 不可以
        //todo: 浏览器中的 cookie 设置都是正确的
        System.out.println(playService.addDrawScoreWithSource(cookieMobile));

        try {

            if (playService.isLogined(cookieMobile)) {
                System.out.println(playService.setDrawMobile(cookieMobile, friendMobile));

                // 累加每日奖励, 并接收返回结果
                double firstShakeGiveCredit = playService.addDrawScore(cookieMobile);

                // 获取剩余次数
                int drawCount = playService.getRemainTimes(cookieMobile);

                while (drawCount > 0) {
                    String drawResult = playService.drawWithSource(cookieMobile);

                    System.out.println(cookieMobile + " draw 返回(" + drawResult + ")");

                    //todo: 这里有可能出错
                    String strDrawCount = JsonUtils.stringToJson(drawResult).getJSONObject("result").getString("drawCount");

                    drawCount = Integer.parseInt(strDrawCount);

                    System.out.println(cookieMobile + " addDrawScore 返回(" + playService.addDrawScoreWithSource(cookieMobile) + ")");


                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {

                    }
                }
            } else {
                System.out.println(cookieMobile + " 没有登录, 无法为" + friendMobile + "摇奖");
            }
        } catch (Exception e) {
            // 设置回自己的 手机号
            System.out.println(cookieMobile + " 为朋友要去过程出错: 设置自己的手机号, 返回(" + playService.setDrawMobile(cookieMobile, cookieMobile) + ")");
        }*/
    }
}
