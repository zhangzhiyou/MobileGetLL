package com.xiayule.getll.toolkit.scheduling.impl;

import com.xiayule.getll.service.PlayService;
import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.toolkit.scheduling.JobTask;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by tan on 14-7-22.
 */
public class JobTaskImpl implements JobTask {
    private PlayService playService;
    private SubscriberService subscriberService;

    public void doJob() {
        List<String> subs = subscriberService.getAllSubscriber();

        /**
         * 执行任务
         */
        for (String sub : subs) {
            System.out.println("JobTask:" + "执行任务:" + "订阅者:" + sub);

            playService.setMobile(sub);

            // 如果未登录, 就登录
            if (!playService.isLogined()) {
                System.out.println(sub + " 未登录, 进行登录");
                playService.loginDo();
                System.out.println(sub + " 登录成功");
            }

            // 累加每日奖励
            playService.addDrawScore();

            // 查询分数
            JSONObject queryScore = playService.queryScore();

            System.out.println("连续登录:" + queryScore.getString("count_1") + "天"
                    + " 今日奖励:" + queryScore.getString("todayCredit")
                    + " 当前流量币: " + queryScore.getString("credit"));

    //            如果已经登录
            int remainTimes = playService.getRemainTimes();

            System.out.println("还剩 " + remainTimes + " 次");

            if (remainTimes > 0) {
                // 有可能抽奖过程中获得再一次奖励

                int cnt = 0;
                do {
                    System.out.print("第" + (++cnt) + "次摇奖,获得奖励:"
                            + playService.draw());

                    playService.addDrawScore();

                    remainTimes = Integer.parseInt(playService.queryScore().getString("times"));

                    System.out.println(" 剩余次数:" + remainTimes);
                } while (remainTimes > 0);
            }
        }
    }

    public void setPlayService(PlayService playService) {
        this.playService = playService;
    }

    public void setSubscriberService(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }
}
