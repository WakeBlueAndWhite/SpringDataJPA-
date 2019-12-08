package com.ceer.test;

import com.ceer.domain.Customer;
import com.ceer.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
/**
 * springdatajpa
 * 	day1:orm思想和hibernate以及jpa的概述和jpa的基本操作
 * 	day2：springdatajpa的运行原理以及基本操作
 * 	day3：多表操作，复杂查询
 *
 * 第一 orm思想
 * 	主要目的：操作实体类就相当于操作数据库表
 * 	建立两个映射关系：
 * 		实体类和表的映射关系
 * 		实体类中属性和表中字段的映射关系
 * 	不再重点关注：sql语句
 *
 * 	实现了ORM思想的框架：mybatis，hibernate
 *
 * 第二 hibernate框架介绍
 * 	Hibernate是一个开放源代码的对象关系映射框架，
 * 		它对JDBC进行了非常轻量级的对象封装，
 * 		它将POJO与数据库表建立映射关系，是一个全自动的orm框架
 *
 * 第三 JPA规范
 * 	jpa规范，实现jpa规范，内部是由接口和抽象类组成
 *
 * 第四 jpa的基本操作
 * 	案例：是客户的相关操作（增删改查）
 * 		客户：就是一家公司
 * 	客户表：
 *
 * 	jpa操作的操作步骤
 * 		1.加载配置文件创建实体管理器工厂
 * 			Persisitence：静态方法（根据持久化单元名称创建实体管理器工厂）
 * 				createEntityMnagerFactory（持久化单元名称）
 * 			作用：创建实体管理器工厂
 *
 * 		2.根据实体管理器工厂，创建实体管理器
 * 			EntityManagerFactory ：获取EntityManager对象
 * 			方法：createEntityManager
 * 			* 内部维护的很多的内容
 * 				内部维护了数据库信息，
 * 				维护了缓存信息
 * 				维护了所有的实体管理器对象
 * 				再创建EntityManagerFactory的过程中会根据配置创建数据库表
 * 			* EntityManagerFactory的创建过程比较浪费资源
 * 			特点：线程安全的对象
 * 				多个线程访问同一个EntityManagerFactory不会有线程安全问题
 * 			* 如何解决EntityManagerFactory的创建过程浪费资源（耗时）的问题？
 * 			思路：创建一个公共的EntityManagerFactory的对象
 * 			* 静态代码块的形式创建EntityManagerFactory
 *
 * 		3.创建事务对象，开启事务
 * 			EntityManager对象：实体类管理器
 * 				beginTransaction : 创建事务对象
 * 				presist ： 保存
 * 				merge  ： 更新
 * 				remove ： 删除
 * 				find/getRefrence ： 根据id查询
 *
 * 			Transaction 对象 ： 事务
 * 				begin：开启事务
 * 				commit：提交事务
 * 				rollback：回滚
 * 		4.增删改查操作
 * 		5.提交事务
 * 		6.释放资源
 *


/**
 * @ProjectName: springdataJPA
 * @Package: com.ceer.test
 * @ClassName: JpaTest
 * @Description: java类作用描述
 * @Author: ceer
 * @CreateDate: 2019/12/6 17:41
 */
public class JpaTest {
    /**
     * 测试jpa的保存
     *      案例：保存一个客户到数据库中
     *  Jpa的操作步骤
     *     1.加载配置文件创建工厂（实体管理器工厂）对象
     *     2.通过实体管理器工厂获取实体管理器
     *     3.获取事务对象，开启事务
     *     4.完成增删改查操作
     *     5.提交事务（回滚事务）
     *     6.释放资源
     */
    @Test
    public void testSave() {
//        //1.加载配置文件创建工厂（实体管理器工厂）对象
//        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("myJpa");
//        //2.通过实体管理器工厂获取实体管理器
//        EntityManager em = managerFactory.createEntityManager();
        //使用工具类
        EntityManager em = JpaUtils.getEntityManager();
        //3.获取事务对象，开启事务
        EntityTransaction tx = em.getTransaction(); //获取事务对象
        tx.begin();//开启事务
        //4.完成增删改查操作：保存一个客户到数据库中
        Customer customer = new Customer();
        customer.setCustName("JOJO的奇妙冒险");
        customer.setCustIndustry("动漫");
        //保存，
        em.persist(customer); //保存操作
        //5.提交事务
        tx.commit();
        //6.释放资源
        em.close();
    }

    /**
     * 根据id查询客户
     *  使用find方法查询：
     *      1.查询的对象就是当前客户对象本身
     *      2.在调用find方法的时候，就会发送sql语句查询数据库
     *
     *  立即加载
     *
     *
     */
    @Test
    public  void testFind() {
        //1.通过工具类获取entityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3.增删改查 -- 根据id查询客户
        /**
         * find : 根据id查询数据
         *      class：查询数据的结果需要包装的实体类类型的字节码
         *      id：查询的主键的取值
         */
        Customer customer = entityManager.find(Customer.class, 1l);
        // System.out.print(customer);
        //4.提交事务
        tx.commit();
        //5.释放资源
        entityManager.close();
    }

    /**
     * 根据id查询客户
     *      getReference方法
     *          1.获取的对象是一个动态代理对象
     *          2.调用getReference方法不会立即发送sql语句查询数据库
     *              * 当调用查询结果对象的时候，才会发送查询的sql语句：什么时候用，什么时候发送sql语句查询数据库
     *
     * 延迟加载（懒加载）
     *      * 得到的是一个动态代理对象
     *      * 什么时候用，什么使用才会查询
     */
    @Test
    public  void testReference() {
        //1.通过工具类获取entityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3.增删改查 -- 根据id查询客户
        /**
         * getReference : 根据id查询数据
         *      class：查询数据的结果需要包装的实体类类型的字节码
         *      id：查询的主键的取值
         */
        Customer customer = entityManager.getReference(Customer.class, 1l);
        System.out.print(customer);
        //4.提交事务
        tx.commit();
        //5.释放资源
        entityManager.close();
    }
    /**
     * 删除客户的案例
     *
     */
    @Test
    public  void testRemove() {
        //1.通过工具类获取entityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3.增删改查 -- 删除客户

        //i 根据id查询客户
        Customer customer = entityManager.find(Customer.class,1l);
        //ii 调用remove方法完成删除操作
        entityManager.remove(customer);

        //4.提交事务
        tx.commit();
        //5.释放资源
        entityManager.close();
    }

    /**
     * 更新客户的操作
     *      merge(Object)
     */
    @Test
    public  void testUpdate() {
        //1.通过工具类获取entityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3.增删改查 -- 更新操作

        //i 查询客户
        Customer customer = entityManager.find(Customer.class,1l);
        //ii 更新客户
        customer.setCustIndustry("好看的动漫");
        entityManager.merge(customer);

        //4.提交事务
        tx.commit();
        //5.释放资源
        entityManager.close();
    }
}
