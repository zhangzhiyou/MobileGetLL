package com.xiayule.getll.toolkit.scheduling.impl;

import com.xiayule.getll.service.EmailService;
import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.toolkit.scheduling.JobTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by tan on 14-11-8.
 */
@Component
public class EmailJob implements JobTask {
    private static Logger logger = LogManager.getLogger(JobTask.class.getName());

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 8,22 * * ?")
    public void doJob() {
        Integer mSubscribCount = subscriberService.countNumbers();

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
