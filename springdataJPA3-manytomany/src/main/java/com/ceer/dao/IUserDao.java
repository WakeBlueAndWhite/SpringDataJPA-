package com.ceer.dao;

import com.ceer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ProjectName: springdataJPA
 * @Package: com.ceer.dao
 * @ClassName: IUserDao
 * @Description: java类作用描述
 * @Author: ceer
 * @CreateDate: 2019/12/8 19:59
 */
public interface IUserDao extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
}
