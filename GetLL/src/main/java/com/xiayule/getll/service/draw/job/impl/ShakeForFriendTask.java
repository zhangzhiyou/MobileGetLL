package com.xiayule.getll.service.draw.job.impl;

import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.service.draw.api.PlayService;
import com.xiayule.getll.service.draw.job.ShakeTask;
import com.xiayule.getll.utils.JsonUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by tan on 14-10-26.
 */
@Component
public class ShakeForFriendTask implements Runnable {

    private static Logger logger = Logger.getLogger(ShakeTask.class);

    @Autowired
    private PlayService playService;

    @Autowired
    private SubscriberService subscriberService;

    private static boolean running = false;

    @Autowired
    private TaskExecutor taskExecutor;

    /**
     * 为朋友摇奖，自己的手机号永远都是固定的。
     * @param friendMobile 朋友的手机号
     */
    public void autoPlay(String friendMobile) {

        final String myMobile = "18369905136";

        if (myMobile == friendMobile) return;

        //todo: addDrawScore 是可以的
        //todo: 但是 load 和draw 不可以
        //todo: 浏览器中的 cookie 设置都是正确的

        playService.addDrawScoreWithSource(myMobile);

        try {

            if (playService.isLogined(myMobile)) {
                logger.info(myMobile + " 为（" + friendMobile + ")摇奖 setDrawMobile返回(" + playService.setDrawMobile(myMobile, friendMobile) + ")");

                Thread.sleep(ShakeTask.PLAY_LAZY);

                // 累加每日奖励, 并接收返回结果
                playService.addDrawScore(myMobile);

                Thread.sleep(ShakeTask.PLAY_LAZY);

                // 获取剩余次数
                int drawCount = playService.getRemainTimes(myMobile);

                Thread.sleep(ShakeTask.PLAY_LAZY);

                int cnt = 0;

                while (drawCount > 0) {
                    String drawResult = playService.drawWithSource(myMobile);

                    logger.info(myMobile + "为(" + friendMobile + ")第(" + ++cnt + ")次摇奖 draw 返回(" + drawResult + ")");

                    //todo: 这里有可能出错
                    String strDrawCount = JsonUtils.stringToJson(drawResult).getJSONObject("result").getString("drawCount");

                    drawCount = Integer.parseInt(strDrawCount);

                    logger.info(myMobile + "为(" + friendMobile + ")摇奖 addDrawScore 返回(" + playService.addDrawScoreWithSource(myMobile) + ")");

                    try {
                        Thread.sleep(ShakeTask.PLAY_LAZY);
                    } catch (Exception e) {
                        logger.info(myMobile + "为朋友摇奖(" + friendMobile + ")" + "Thread.sleep error");
                    }
                }
            } else {
                logger.info(myMobile + " 没有登录, 无法为" + friendMobile + "摇奖");
            }
        } catch (Exception e) {
            // 设置回自己的 手机号
            logger.info(myMobile + " 为朋友(" + friendMobile + ")摇取过程出错");
        } finally {// 无论是否出错，执行完毕后都要设置回自己的手机号
            logger.info(myMobile + " 为朋友(" + friendMobile + ")摇取完毕, 设置自己的手机号, 返回(" + playService.setDrawMobile(myMobile, myMobile) + ")");
        }
    }

    private int status = 0;

    public void taskStart() {
        status = 0;

        logger.info("JobForFriendTaskImpl:开始为朋友摇奖");

        if (!running) {

            running = true;

            // 获取所有且有效的订阅下午摇奖的人,
            List<String> subs = subscriberService.getAllSubscriberForFriend();

            for (String sub : subs) {
                try {
                    // 如果有效期到期或者登录不成功, 则不执行
                    if (/*!subscriberService.isSubscribe(sub) && */!playService.isLogined(sub)) continue;

                    autoPlay(sub);

                    status++;
                } catch (Exception e) {
                    logger.info("JobForFriendTaskImpl: 为(" + sub + ")朋友摇奖发生错误");
                }
            }

            logger.info("JobForFriendTaskImpl:" + "将 " + status + " 个任务加入队列");

            running = false;

        } else {
            logger.info("JobForFriendTaskImpl:" + "任务已经开启，无需再开启");
        }
    }

    @Override
    public void run() {
        taskStart();
    }

    @Scheduled(cron = "0 0 18 * * ?")
    public void startExecute() {
        taskExecutor.execute(this);
    }

    public void setPlayService(PlayService playService) {
        this.playService = playService;
    }

    public int getStatus() {
        return status;
    }

    public static boolean isRunning() {
        return running;
    }
}
