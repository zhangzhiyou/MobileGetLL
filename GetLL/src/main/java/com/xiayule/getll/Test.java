package com.xiayule.getll;

import com.xiayule.getll.action.AdminAction;
import com.xiayule.getll.mail.Email;
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
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//        PlayService playService = ctx.getBean("playService", PlayService.class);

//        System.out.println(playService.smsNoticeSet("18369905136", "fdShakeNotify", "1"));

//        Email email = ctx.getBean("simpleMail", Email.class);
//        email.sendMail("xiayule148@gmail.com", "测试", "测试");


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

        String mobile = "18369905136";

        try {
            String rs = "{\"message\":\"ok\",\"result\":{\"loginMobile\":null,\"seconds\":0,\"nickName\":null,\"drawMobile\":null,\"loginMobileProvinceId\":null,\"drawMobileProvinceId\":null},\"status\":\"ok\",\"class\":\"class com.aspire.portal.web.vo.JsonResult\",\"code\":\"\"}";
            System.out.println(rs);

            String loginMobile = getFromResult(rs, "loginMobile");

            System.out.println(loginMobile);

            //todo: 看了下日志,如果加载失败返回null,不应该删除cookie
            //todo: 这个应该放到 loadLoginMobile 里面?
            if (loginMobile == null || loginMobile.equals("") || loginMobile.equals("null")) {
                System.out.println("为空");
                return ;
            }

            // 只有当加载出来手机号了,且加载的手机号不为null, 才删除cookie
            if ( !loginMobile.equals(mobile)) {

                System.out.println("删除cookie");

                return ;
            } else return ;
        } catch (Exception e) {
            return ;
        }
    }

    private static String getFromResult(String json, String key) {
        String value = JsonUtils.stringToJson(json).getJSONObject("result").getString(key);

        /*Map<String, String> mp = JsonUtils.jsonToHash(json);
        Map<String, String> resultJson = JsonUtils.jsonToHash(mp.get("result"));
        String value = resultJson.get(key);*/
        return value;
    }
}
