package com.xiayule.getll;

import com.xiayule.getll.action.AdminAction;
import com.xiayule.getll.service.PlayService;
import com.xiayule.getll.utils.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tan on 14-7-20.
 */
public class Test {
    public static void main(String[] args) {
        /*ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        PlayService playService = ctx.getBean("playService", PlayService.class);

        System.out.println(playService.smsNoticeSet("18369905136", "fdShakeNotify", "1"));
*/

        JavaMailSenderImpl sender = new JavaMailSenderImpl();

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

        System.out.println("发送邮件成功");
    }
}
