package com.ceer.test;

import com.ceer.dao.ICustomerDao;
import com.ceer.dao.ILinkManDao;
import com.ceer.domain.Customer;
import com.ceer.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
/**多表之间的关系和操作多表的操作步骤

 表关系
 一对一
 一对多：
 一的一方：主表
 多的一方：从表
 外键：需要再从表上新建一列作为外键，他的取值来源于主表的主键
 多对多：
 中间表：中间表中最少应该由两个字段组成，这两个字段做为外键指向两张表的主键，又组成了联合主键

 讲师对学员：一对多关系

 实体类中的关系
 包含关系：可以通过实体类中的包含关系描述表关系
 继承关系

 分析步骤
 1.明确表关系
 2.确定表关系（描述 外键|中间表）
 3.编写实体类，再实体类中描述表关系（包含关系）
 4.配置映射关系

 * i.一对多操作
 * 		案例：客户和联系人的案例（一对多关系）
 * 			客户：一家公司
 * 			联系人：这家公司的员工
 *
 * 			一个客户可以具有多个联系人
 * 			一个联系人从属于一家公司
 *
 * 		分析步骤
 * 			1.明确表关系
 * 				一对多关系
 * 			2.确定表关系（描述 外键|中间表）
 * 				主表：客户表
 * 				从表：联系人表
 * 					* 再从表上添加外键
 * 			3.编写实体类，再实体类中描述表关系（包含关系）
 * 				客户：再客户的实体类中包含一个联系人的集合
 * 				联系人：在联系人的实体类中包含一个客户的对象
 * 			4.配置映射关系
 * 				* 使用jpa注解配置一对多映射关系
 */

/**
 * @ProjectName: springdataJPA
 * @Package: com.ceer.test
 * @ClassName: OneToManyTest
 * @Description: java类作用描述
 * @Author: ceer
 * @CreateDate: 2019/12/8 0:00
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OneToManyTest {

    @Autowired
    private ICustomerDao iCustomerDao;

    @Autowired
    private ILinkManDao iLinkManDao;

    /**
     * 保存一个客户，保存一个联系人
     *  效果：客户和联系人作为独立的数据保存到数据库中
     *      联系人的外键为空
     *  原因？
     *      实体类中没有配置关系
     */
    @Test
    @Transactional //配置事务
    @Rollback(value = false) //不自动回滚
    public void testAdd() {
        //创建一个客户，创建一个联系人
        Customer customer = new Customer();
        customer.setCustName("百度");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("小张");

        /**
         * 配置了客户到联系人的关系
         *      从客户的角度上：发送两条insert语句，发送一条更新语句更新数据库（更新外键）
         * 由于我们配置了客户到联系人的关系：客户可以对外键进行维护
         */
        customer.getLinkMen().add(linkMan);
        linkMan.setCustomer(customer);

        iCustomerDao.save(customer);
        iLinkManDao.save(linkMan);
    }
    /**
     * 级联：
     * 			操作一个对象的同时操作他的关联对象
     *
     * 			级联操作：
     * 				1.需要区分操作主体
     * 				2.需要在操作主体的实体类上，添加级联属性（需要添加到多表映射关系的注解上）
     * 				3.cascade（配置级联）
     *
     * 			级联添加，
     * 				案例：当我保存一个客户的同时保存联系人
     * 			级联删除
     * 				案例：当我删除一个客户的同时删除此客户的所有联系人
     */
    /**
     * 级联添加：保存一个客户的同时，保存客户的所有联系人
     *      需要在操作主体的实体类上，配置casacde属性
     */
    @Test
    @Transactional //配置事务
    @Rollback(false) //不自动回滚
    public void testCascadeAdd() {
        Customer customer = new Customer();
        customer.setCustName("百度1");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("小李1");

        linkMan.setCustomer(customer);
        customer.getLinkMen().add(linkMan);

        iCustomerDao.save(customer);
    }


    /**
     * 级联删除：
     *      删除1号客户的同时，删除1号客户的所有联系人
     */
    @Test
    @Transactional //配置事务
    @Rollback(false) //不自动回滚
    public void testCascadeRemove() {
        //1.查询1号客户
        Customer customer = iCustomerDao.findOne(1l);
        //2.删除1号客户
        iCustomerDao.delete(customer);
    }
}
