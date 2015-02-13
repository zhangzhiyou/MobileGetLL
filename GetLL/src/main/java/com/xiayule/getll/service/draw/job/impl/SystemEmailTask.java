package com.xiayule.getll.service.draw.job.impl;

import com.xiayule.getll.service.EmailService;
import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.service.draw.job.ScheduledTask;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by tan on 14-11-8.
 */
@Component
public class SystemEmailTask {
    private static Logger logger = Logger.getLogger(ScheduledTask.class);

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private EmailService emailService;

//    @Scheduled(cron = "0 0 8,22 * * ?")
@Scheduled(cron = "0 41 8,22 * * ?")
    public void doJob() {
        Long mSubscribCount = subscriberService.countNumbers();

        String messageContent = "订阅人数: " + mSubscribCount;

        emailService.sendMail("xiayule148@foxmail.com", "流量汇管家", messageContent);

        logger.info("发送系统邮件到(xiayule148@foxmail.com), 邮件内容(" + messageContent + ");");
    }

    public void setSubscriberService(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}
