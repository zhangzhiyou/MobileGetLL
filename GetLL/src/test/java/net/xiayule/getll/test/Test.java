package net.xiayule.getll.test;

import com.xiayule.getll.db.model.MobileGroup;
import com.xiayule.getll.db.service.FunctionService;
import com.xiayule.getll.db.service.MobileGroupService;
import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:spring-hibernate.xml"})
public class Test extends TestCase {

//    @Autowired
    private MobileGroupService mobileGroupService;

//    @Autowired
    private FunctionService functionService;

//    @org.junit.Test
    public void testSave() {
        MobileGroup mobileGroup = new MobileGroup("18369905136");
        mobileGroupService.save(mobileGroup);
    }

//    @org.junit.Test
    public void testAddToGroup() {
//        两个都没有组的情况
        mobileGroupService.addToGroup("1", "2");

//        一个有组，一个没有组的情况
        mobileGroupService.save(new MobileGroup("3"));
        mobileGroupService.addToGroup("3", "4");

        mobileGroupService.save(new MobileGroup("6"));
        mobileGroupService.addToGroup("5", "6");

//        两个都有组的情况
        mobileGroupService.save(new MobileGroup("7"));
        mobileGroupService.save(new MobileGroup("8"));
        mobileGroupService.addToGroup("7", "8");
    }

//    @org.junit.Test
    public void testExist() {
        System.out.println(mobileGroupService.exist("183699051316"));
    }

//    @org.junit.Test
    public void testGetGroup() {
        System.out.println(mobileGroupService.getGroup("1"));
        System.out.println();
        System.out.println(mobileGroupService.getGroup("2"));
    }

//    @org.junit.Test
    public void testGet() {
        MobileGroup mobileGroup = mobileGroupService.get("18369905136");
        System.out.println(mobileGroup);

        mobileGroup = mobileGroupService.get("18369905137");
        System.out.println(mobileGroup);
    }

//    @org.junit.Test
    public void testDelete() {
        mobileGroupService.delete("18369905136");
    }
}