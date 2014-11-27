package com.xiayule.getll;

import com.xiayule.getll.db.model.HistoryVersion;
import com.xiayule.getll.db.service.HistoryVersionService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;


/**
 * Created by tan on 14-7-20.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] {
                "spring-hibernate.xml"
        });

        /*OwnService ownService = ctx.getBean("ownService", OwnService.class);
        ownService.transferGiftsReceiveAll("18369905136");*/

        HistoryVersionService historyVersionService = ctx.getBean("versionHistoryServicve", HistoryVersionService.class);

        HistoryVersion historyVersion = new HistoryVersion();
        historyVersion.setTitle("第版本");
        historyVersion.setContent("修复了一些bug");
        historyVersion.setVersionName("3.7.2");
        historyVersion.setTime(new Date());

        historyVersion.setId(1);

        historyVersionService.saveVersionHistory(historyVersion);
        System.out.println(historyVersionService.getVersionHistory(1));

        historyVersionService.deleteVersionHistory(2);
        historyVersionService.deleteVersionHistory(historyVersion);

        List<HistoryVersion> versionHistories = historyVersionService.findAllVersionHistory();
        for (HistoryVersion v : versionHistories) {
            System.out.println(v);
        }


//        PlayService playService = ctx.getBean("playService", PlayService.class);

//        System.out.println(playService.smsNoticeSet("18369905136", "fdShakeNotify", "1"));

      /*  EmailServiceImpl emailServiceImpl = ctx.getBean("emailService", EmailServiceImpl.class);
        MailSender mailSender = ctx.getBean("mailSender", MailSender.class);
*/
        /*SimpleMailMessage simpleMessage = ctx.getBean("simpleMailMessage", SimpleMailMessage.class);

        // 设置昵称
        try {
            String nick = javax.mail.internet.MimeUtility.encodeText("流量汇管家");
            System.out.println(nick);
            simpleMessage.setFrom(nick + "<443016215@163.com>");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        simpleMessage.setSubject("哈"); //设置邮件主题
        simpleMessage.setTo("443016215@qq.com");             //设定收件人
        simpleMessage.setText("哈");  //设置邮件主题内容

        mailSender.send(simpleMessage);*/

//        emailServiceImpl.sendMail("443016215@qq.com", "小测试", "测试");


/*
        SubscriberService subscriberService = ctx.getBean("subscribeService", SubscriberService.class);

        subscriberService.subForFriend("18369905136");

*/


        /*EmailJob emailJob = ctx.getBean("emailJob", EmailJob.class);

        emailJob.doJob();*/

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
