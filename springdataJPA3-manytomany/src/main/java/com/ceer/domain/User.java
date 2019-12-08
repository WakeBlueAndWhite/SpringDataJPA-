package com.ceer.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
/**
 * 在进行多对多配置后，在测试方法中尝试使用获取一方信息，结果出现了内存溢出的错误。
 *
 * 总结一下原因以及解决方案：
 *
 * 原因一：为了方便看信息,在两类中分别重写了 toString 方法,导致查询加载时两类在互相调用对方的toString,形成递归,造成内存溢出。
 * 解决方案： 在 toString 方法中任意一方去除打印的对方信息。
 *
 * 原因二： 为了编写方便简洁，代码更加优雅，使用了 lombok 插件中的@Data以及@ToString注解来标注类，让 lombok 来代替生成 gettet/setter 以及 toString，但是 lombok 在生成时会出现循环比较两类中的 hashcode，导致内存溢出。
 * 解决方案： 不要使用 lombok ，自己手写。
 */

/**
 * @ProjectName: springdataJPA
 * @Package: com.ceer.domain
 * @ClassName: User
 * @Description: java类作用描述
 * @Author: ceer
 * @CreateDate: 2019/12/8 20:00
 */
@Entity
@Table(name = "sys_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;
    @Column(name="user_name")
    private String userName;
    @Column(name="age")
    private Integer age;

    /**
     * 配置用户到角色的多对多关系
     *      配置多对多的映射关系
     *          1.声明表关系的配置
     *              @ManyToMany(targetEntity = Role.class)  //多对多
     *                  targetEntity：代表对方的实体类字节码
     *          2.配置中间表（包含两个外键）
     *                @JoinTable
     *                  name : 中间表的名称
     *                  joinColumns：配置当前对象在中间表的外键
     *                      @JoinColumn的数组
     *                          name：外键名
     *                          referencedColumnName：参照的主表的主键名
     *                  inverseJoinColumns：配置对方对象在中间表的外键
     */
    @ManyToMany(targetEntity = Role.class,cascade = CascadeType.ALL)
    @JoinTable(name = "sys_user_role",
            //joinColumns,当前对象在中间表中的外键
            joinColumns = {@JoinColumn(name = "sys_user_id",referencedColumnName = "user_id")},
            //inverseJoinColumns，对方对象在中间表的外键
            inverseJoinColumns = {@JoinColumn(name = "sys_role_id",referencedColumnName = "role_id")}
    )
    private Set<Role> roles = new HashSet<Role>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
