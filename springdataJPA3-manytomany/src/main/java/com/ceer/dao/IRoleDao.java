package com.ceer.dao;

import com.ceer.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ProjectName: springdataJPA
 * @Package: com.ceer.dao
 * @ClassName: IRoleDao
 * @Description: java类作用描述
 * @Author: ceer
 * @CreateDate: 2019/12/8 20:00
 */
public interface IRoleDao extends JpaRepository<Role,Long>, JpaSpecificationExecutor<Role> {
}
