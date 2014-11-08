package com.xiayule.getll.service;

/**
 * Created by tan on 14-11-8.
 */
public interface EmailService {
    /**
     * @方法名: sendMail
     * @参数名：@param to         收件人Email地址
     * @参数名：@param subject  邮件主题
     * @参数名：@param content 邮件主题内容
     * @描述语: 发送邮件
     */
    public void sendMail(String to, String subject, String content);
}
