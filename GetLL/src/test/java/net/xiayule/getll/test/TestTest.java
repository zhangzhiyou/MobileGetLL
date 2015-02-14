package net.xiayule.getll.test;

import com.xiayule.getll.db.model.Function;
import com.xiayule.getll.db.model.MobileAccount;
import com.xiayule.getll.db.service.FunctionService;
import com.xiayule.getll.db.service.MobileAccountService;
import com.xiayule.getll.service.CookieService;
import com.xiayule.getll.service.RedisService;
import com.xiayule.getll.service.impl.CookieServiceImpl;
import com.xiayule.getll.service.impl.RedisDataSourceImpl;
import com.xiayule.getll.service.impl.RedisServiceImpl;
import junit.framework.TestCase;
import org.apache.http.client.CookieStore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-hibernate.xml",
        "classpath*:spring-redis.xml"})
public class TestTest extends TestCase {

    @Autowired
    private CookieService cookieService;

    @Autowired
    private MobileAccountService mobileAccountService;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private FunctionService functionService;


    // 把 redis 中的订阅者数据添加到 mysql 中, 即 mobile_account 表
    @Test
    public void transRedisSubToMysql() {
        List<String> subscribers = getAllSubscriber();

        System.out.println(subscribers.size());

        for (String s : subscribers) {
            MobileAccount mobileAccount = new MobileAccount(s);

            mobileAccountService.save(mobileAccount);
        }

        System.out.println("导入完毕");
    }

    // 把 redis 中的cookie数据添加到 mysql 中, 即 cookie 表
    @Test
    public void transRedisCookieToMysql() {
        List<String> subscribers = getAllSubscriber();

        System.out.println(subscribers.size());

        CookieServiceImpl cookieServiceRedis = getCookieServiceRedis();;

        for (String s : subscribers) {
            CookieStore cookieStore = cookieServiceRedis.getCookieStore(s);
            cookieService.saveCookie(s, cookieStore);
        }

        System.out.println("导入完毕");
    }

    // 把 redis 中的 功能 数据添加到 mysql 中, 即 function 表
    @Test
    public void transRedisFunctionToMysql() {
        RedisService redisService = getRedisService();

        List<String> subscribers = getAllSubscriber();

        System.out.println(subscribers.size());

        for (String s : subscribers) {
            Function function = new Function();
            function.setMobile(s);

            boolean forFriend = redisService.sismember("forFriend", s);
            boolean autoReceive = redisService.sismember("autoReceive", s);

            function.setAutoReceive(autoReceive);
            function.setForFriend(forFriend);

            functionService.save(function);
        }

        System.out.println("导入数据完毕");
    }

    // 获得所有的订阅者
    public List<String> getAllSubscriber() {
        Set<String> subs = getRedisService().keys("sub_*");

        List<String> subList = new ArrayList<String>();

        for (String e : subs) {
            subList.add(e.replace("sub_", ""));
        }

        return subList;
    }


    public RedisService getRedisService() {
        RedisDataSourceImpl redisDataSource = new RedisDataSourceImpl();
        redisDataSource.setJedisPool(jedisPool);

        RedisServiceImpl redisService = new RedisServiceImpl();
        redisService.setRedisDataSource(redisDataSource);

        return redisService;
    }

    // 手动创建 redis cookie service
    public CookieServiceImpl getCookieServiceRedis() {

        CookieServiceImpl cookieServiceRedis = new CookieServiceImpl();
        cookieServiceRedis.setRedisService(getRedisService());

        return cookieServiceRedis;
    }
}