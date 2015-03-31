package net.xiayule.getll.test;

import com.xiayule.getll.service.CookieService;
import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-hibernate.xml"})
public class Test extends TestCase {

    @Autowired
    private CookieService cookieService;


}