package com.xiayule.getll;

import com.xiayule.getll.service.impl.EmailServiceImpl;
import com.xiayule.getll.toolkit.scheduling.impl.EmailJob;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by tan on 14-7-20.
 */
public class Test {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] {
                "spring-mail.xml", "spring-quartz.xml", "applicationContext.xml"
        });
//        PlayService playService = ctx.getBean("playService", PlayService.class);

//        System.out.println(playService.smsNoticeSet("18369905136", "fdShakeNotify", "1"));

        /*EmailServiceImpl emailServiceImpl = ctx.getBean("emailService", EmailServiceImpl.class);
        emailServiceImpl.sendMail("xiayule148@gmail.com", "小测试", "测试");
        */

        EmailJob emailJob = ctx.getBean("emailJob", EmailJob.class);

        emailJob.doJob();

       /* JavaMailSenderImpl sender = new JavaMailSenderImpl();

        // 设定mail server
        sender.setHost("smtp.163.com");

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo("xiayule148@gmail.com");
        mailMessage.setFrom("443016215@163.com");
        mailMessage.setSubject("服务通知");
        mailMessage.setText("测试");

        sender.setUsername("443016215@163.com");
        sender.setPassword("~6224989a");

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.timeout", "25000");
        sender.setJavaMailProperties(prop);

        sender.send(mailMessage);

        System.out.println("发送邮件成功");*/

    }
}
