package com.ceer.test;

import com.ceer.dao.ICustomerDao;
import com.ceer.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * SpringDataJpa第二天
 * 	orm思想，hibernate，JPA的相关操作
 *
 * * SpringDataJpa
 *
 * 第一 springDataJpa的概述
 *
 * 第二 springDataJpa的入门操作
 * 	案例：客户的基本CRUD
 * 	i.搭建环境
 * 		创建工程导入坐标
 * 		配置spring的配置文件（配置spring Data jpa的整合）
 * 		编写实体类（Customer），使用jpa注解配置映射关系
 * 	ii.编写一个符合springDataJpa的dao层接口
 * 		* 只需要编写dao层接口，不需要编写dao层接口的实现类
 * 		* dao层接口规范
 * 			1.需要继承两个接口（JpaRepository，JpaSpecificationExecutor）
 * 			2.需要提供响应的泛型
 *
 * 	*
 * 		findOne（id） ：根据id查询
 * 		save(customer):保存或者更新（依据：传递的实体类对象中，是否包含id属性）
 * 		delete（id） ：根据id删除
 * 		findAll() : 查询全部
 *
 * 第三 springDataJpa的运行过程和原理剖析
 * 	1.通过JdkDynamicAopProxy的invoke方法创建了一个动态代理对象
 * 	2.SimpleJpaRepository当中封装了JPA的操作（借助JPA的api完成数据库的CRUD）
 * 	3.通过hibernate完成数据库操作（封装了jdbc）
 *
 *
 * 第四 复杂查询
 * 	i.借助接口中的定义好的方法完成查询
 * 		findOne(id):根据id查询
 * 	ii.jpql的查询方式
 * 		jpql ： jpa query language  （jpq查询语言）
 * 		特点：语法或关键字和sql语句类似
 * 			查询的是类和类中的属性
 *
 * 		* 需要将JPQL语句配置到接口方法上
 * 			1.特有的查询：需要在dao接口上配置方法
 * 			2.在新添加的方法上，使用注解的形式配置jpql查询语句
 * 			3.注解 ： @Query
 *
 * 	iii.sql语句的查询
 * 			1.特有的查询：需要在dao接口上配置方法
 * 			2.在新添加的方法上，使用注解的形式配置sql查询语句
 * 			3.注解 ： @Query
 * 				value ：jpql语句 | sql语句
 * 				nativeQuery ：false（使用jpql查询） | true（使用本地查询：sql查询）
 * 					是否使用本地查询
 *
 * 	iiii.方法名称规则查询
 *
 *
 */

/**
 * @ProjectName: springdataJPA
 * @Package: com.ceer.test
 * @ClassName: CustomerDaoTest
 * @Description: java类作用描述
 * @Author: ceer
 * @CreateDate: 2019/12/7 0:10
 */
@RunWith(SpringJUnit4ClassRunner.class) //声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml")//指定spring容器的配置信息
public class CustomerDaoTest {

    @Autowired
    private ICustomerDao iCustomerDao;

    /**
     * 根据id查询
     */
    @Test
    public void testFindOne() {
        Customer customer = iCustomerDao.findOne(1l);
        System.out.println(customer);
    }

    /**
     * save : 保存或者更新
     *      根据传递的对象是否存在主键id，
     *      如果没有id主键属性：保存
     *      存在id主键属性，根据id查询数据，更新数据
     */
    @Test
    public void testSave() {
        Customer customer  = new Customer();
        customer.setCustName("永生之酒");
        customer.setCustLevel("vip");
        customer.setCustIndustry("动漫");
        iCustomerDao.save(customer);
    }

    @Test
    public void testUpdate() {
        Customer customer  = new Customer();
        customer.setCustId(4l);
        customer.setCustName("无头骑士异闻录");
        iCustomerDao.save(customer);
    }


    /**
     * 删除 根据id删除
     */
    @Test
    public void testDelete () {
        iCustomerDao.delete(3l);
    }

    /**
     * 查询所有
     */
    @Test
    public void testFindAll() {
        List<Customer> list = iCustomerDao.findAll();
        for(Customer customer : list) {
            System.out.println(customer);
        }
    }

    /**
     * 测试统计查询：查询客户的总数量
     *      count:统计总条数
     */
    @Test
    public void testCount() {
        long count = iCustomerDao.count();//查询全部的客户数量
        System.out.println(count);
    }

    /**
     * 测试：判断id为4的客户是否存在
     *      1. 可以查询以下id为4的客户
     *          如果值为空，代表不存在，如果不为空，代表存在
     *      2. 判断数据库中id为4的客户的数量
     *          如果数量为0，代表不存在，如果大于0，代表存在
     */
    @Test
    public void  testExists() {
        boolean exists = iCustomerDao.exists(4l);
        System.out.println("id为4是否存在："+exists);
    }

    /**
     * 根据id从数据库查询
     *      @Transactional : 保证getOne正常运行
     *
     *  findOne：
     *      em.find()           :立即加载
     *  getOne：
     *      em.getReference     :延迟加载
     *      * 返回的是一个客户的动态代理对象
     *      * 什么时候用，什么时候查询
     */
    @Test
    @Transactional
    public void  testGetOne() {
        Customer customer = iCustomerDao.getOne(4l);
        System.out.println(customer);
    }

}
