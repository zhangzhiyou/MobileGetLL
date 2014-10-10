package com.xiayule.getll;

import com.xiayule.getll.action.AdminAction;
import com.xiayule.getll.service.PlayService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tan on 14-7-20.
 */
public class Test {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        AdminAction adminAction = ctx.getBean("adminAction", AdminAction.class);

        System.out.println(adminAction.countSubscribers());

    }
}
