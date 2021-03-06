package com.ceer.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @ProjectName: springdataJPA
 * @Package: com.ceer.domain
 * @ClassName: Customer
 * @Description: java类作用描述
 * @Author: ceer
 * @CreateDate: 2019/12/6 23:55
 */
@Entity
@Table(name = "cst_customer")
@Data
public class Customer {
    /**
     * @Id：声明主键的配置
     * @GeneratedValue:配置主键的生成策略
     *      strategy
     *          GenerationType.IDENTITY ：自增，mysql
     *                 * 底层数据库必须支持自动增长（底层数据库支持的自动增长方式，对id自增）
     *          GenerationType.SEQUENCE : 序列，oracle
     *                  * 底层数据库必须支持序列
     *          GenerationType.TABLE : jpa提供的一种机制，通过一张数据库表的形式帮助我们完成主键自增
     *          GenerationType.AUTO ： 由程序自动的帮助我们选择主键生成策略
     * @Column:配置属性和字段的映射关系
     *      name：数据库表中字段的名称
     */

    /**
     * 客户的主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Long custId;
    /**
     * 客户名称
     */
    @Column(name = "cust_name")
    private String custName;
    /**
     * 客户来源
     */
    @Column(name="cust_source")
    private String custSource;
    /**
     * 客户级别
     */
    @Column(name="cust_level")
    private String custLevel;
    /**
     * 客户所属行业
     */
    @Column(name="cust_industry")
    private String custIndustry;
    /**
     * 客户的联系方式
     */
    @Column(name="cust_phone")
    private String custPhone;
    /**
     * 客户地址
     */
    @Column(name="cust_address")
    private String custAddress;
}

