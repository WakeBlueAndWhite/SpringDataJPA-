package com.ceer.test;

import com.ceer.dao.IRoleDao;
import com.ceer.dao.IUserDao;
import com.ceer.domain.Role;
import com.ceer.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
/**
 * 多对多操作
 * 		案例：用户和角色（多对多关系）
 * 			用户：
 * 			角色：
 *
 * 		分析步骤
 * 			1.明确表关系
 * 				多对多关系
 * 			2.确定表关系（描述 外键|中间表）
 * 				中间间表
 * 			3.编写实体类，再实体类中描述表关系（包含关系）
 * 				用户：包含角色的集合
 * 				角色：包含用户的集合
 * 			4.配置映射关系
 */

/**
 * @ProjectName: springdataJPA
 * @Package: com.ceer.test
 * @ClassName: ManyToManyTest
 * @Description: java类作用描述
 * @Author: ceer
 * @CreateDate: 2019/12/8 20:06
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ManyToManyTest {

    @Autowired
    private IUserDao iUserDao;
    @Autowired
    private IRoleDao IRoleDao;

    /**
     * 保存一个用户，保存一个角色
     *
     *  多对多放弃维护权：被动的一方放弃
     */
    @Test
    @Transactional
    @Rollback(false)
    public void  testAdd() {
        User user = new User();
        user.setUserName("小李");

        Role role = new Role();
        role.setRoleName("java程序员");

        //配置用户到角色关系，可以对中间表中的数据进行维护     1-1
        user.getRoles().add(role);

        //配置角色到用户的关系，可以对中间表的数据进行维护     1-1
        role.getUsers().add(user);

        iUserDao.save(user);
        IRoleDao.save(role);
    }

    //测试级联添加（保存一个用户的同时保存用户的关联角色）
    @Test
    @Transactional
    @Rollback(false)
    public void  testCasCadeAdd() {
        User user = new User();
        user.setUserName("小李");

        Role role = new Role();
        role.setRoleName("java程序员");

        //配置用户到角色关系，可以对中间表中的数据进行维护     1-1
        user.getRoles().add(role);

        //配置角色到用户的关系，可以对中间表的数据进行维护     1-1
        role.getUsers().add(user);

        iUserDao.save(user);
    }

    /**
     * 案例：删除id为1的用户，同时删除他的关联对象
     */
    @Test
    @Transactional
    @Rollback(false)
    public void  testCasCadeRemove() {
        //查询1号用户
        User user = iUserDao.findOne(1l);
        //删除1号用户
        iUserDao.delete(user);

    }
}
