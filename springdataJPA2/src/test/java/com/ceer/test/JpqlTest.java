package com.ceer.test;

import com.ceer.dao.ICustomerDao;
import com.ceer.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @ProjectName: springdataJPA
 * @Package: com.ceer.test
 * @ClassName: JpqlTest
 * @Description: java类作用描述
 * @Author: ceer
 * @CreateDate: 2019/12/7 17:26
 */
@RunWith(SpringJUnit4ClassRunner.class) //声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml")//指定spring容器的配置信息
public class JpqlTest {

    @Autowired
    private ICustomerDao iCustomerDao;

    @Test
    public void testFindJPQL() {
        Customer customer = iCustomerDao.findJpql("JOJO的奇妙冒险");
        System.out.println(customer);
    }

    @Test
    public void testFindCustNameAndId() {
        // Customer customer =  customerDao.findCustNameAndId("传智播客",1l);
        Customer customer = iCustomerDao.findCustNameAndId(1l, "侧耳倾听1");
        System.out.println(customer);
    }

    /**
     * 测试jpql的更新操作
     * * springDataJpa中使用jpql完成 更新/删除操作
     * * 需要手动添加事务的支持
     * * 默认会执行结束之后，回滚事务
     *
     * @Rollback : 设置是否自动回滚
     * false | true
     */
    @Test
    @Transactional //添加事务的支持
    @Rollback(value = false)
    public void testUpdateCustomer() {
        iCustomerDao.updateCustomer(4l, "野良神");
    }

    //测试sql查询
    @Test
    public void testFindSql() {
        List<Object[]> list = iCustomerDao.findSql("侧耳倾听%");
        for (Object[] obj : list) {
            System.out.println(Arrays.toString(obj));
        }
    }

    //测试方法命名规则的查询
    @Test
    public void testNaming() {
        Customer customer = iCustomerDao.findByCustName("JOJO的奇妙冒险");
        System.out.println(customer);
    }

    //测试方法命名规则的查询
    @Test
    public void testFindByCustNameLike() {
        List<Customer> list = iCustomerDao.findByCustNameLike("侧耳倾听%");
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    //测试方法命名规则的查询
    @Test
    public void testFindByCustNameLikeAndCustIndustry() {
        List<Customer> customerList = iCustomerDao.findByCustNameLikeAndCustIndustry("侧耳倾听%", "动漫");
        for (Customer customer : customerList) {
            System.out.println(customer);
        }
    }

}

