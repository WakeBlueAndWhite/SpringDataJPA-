package com.ceer.dao;

import com.ceer.domain.Customer;
import com.ceer.domain.LinkMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ProjectName: springdataJPA
 * @Package: com.ceer.dao
 * @ClassName: ICustomerDao
 * @Description: java类作用描述
 * @Author: ceer
 * @CreateDate: 2019/12/7 21:14
 */
public interface ILinkManDao extends JpaRepository<LinkMan,Long>, JpaSpecificationExecutor<LinkMan> {
}
