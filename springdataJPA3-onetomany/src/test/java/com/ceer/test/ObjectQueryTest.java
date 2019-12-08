package com.ceer.test;

import com.ceer.dao.ICustomerDao;
import com.ceer.dao.ILinkManDao;
import com.ceer.domain.Customer;
import com.ceer.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
/**
 *1.对象导航查询
 * 			查询一个对象的同时，通过此对象查询他的关联对象
 *
 * 			案例：客户和联系人
 *
 * 			从一方查询多方
 * 				* 默认：使用延迟加载（****）
 *
 * 			从多方查询一方
 * 				* 默认：使用立即加载
 *
 */

/**
 * @ProjectName: springdataJPA
 * @Package: com.ceer.test
 * @ClassName: ObjectQueryTest
 * @Description: java类作用描述
 * @Author: ceer
 * @CreateDate: 2019/12/8 20:37
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ObjectQueryTest {
    @Autowired
    private ICustomerDao customerDao;

    @Autowired
    private ILinkManDao linkManDao;

    @Test
    @Transactional // 解决在java代码中的no session问题
    public void  testQuery1() {
        //查询id为1的客户
        Customer customer = customerDao.getOne(1l);
        //对象导航查询，此客户下的所有联系人
        Set<LinkMan> linkMans = customer.getLinkMen();

        for (LinkMan linkMan : linkMans) {
            System.out.println(linkMan);
        }
    }


    /**
     * 对象导航查询：
     *      默认使用的是延迟加载的形式查询的
     *          调用get方法并不会立即发送查询，而是在使用关联对象的时候才会差和讯
     *      延迟加载！
     * 修改配置，将延迟加载改为立即加载
     *      fetch，需要配置到多表映射关系的注解上
     *
     */

    @Test
    @Transactional // 解决在java代码中的no session问题
    public void  testQuery2() {
        //查询id为1的客户
        Customer customer = customerDao.findOne(1l);
        //对象导航查询，此客户下的所有联系人
        Set<LinkMan> linkMans = customer.getLinkMen();

        System.out.println(linkMans.size());
    }

    /**
     * 从联系人对象导航查询他的所属客户
     *      * 默认 ： 立即加载
     *  延迟加载：
     *
     */
    @Test
    @Transactional // 解决在java代码中的no session问题
    public void  testQuery3() {
        LinkMan linkMan = linkManDao.findOne(2l);
        //对象导航查询所属的客户
        Customer customer = linkMan.getCustomer();
        System.out.println(customer);
    }
}
