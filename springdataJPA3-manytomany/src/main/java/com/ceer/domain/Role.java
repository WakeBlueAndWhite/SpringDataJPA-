package com.ceer.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @ProjectName: springdataJPA
 * @Package: com.ceer.domain
 * @ClassName: Role
 * @Description: java类作用描述
 * @Author: ceer
 * @CreateDate: 2019/12/8 20:01
 */
@Entity
@Table(name = "sys_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;
    @Column(name = "role_name")
    private String roleName;

    /**配置多对多
     * 配置多表关系
     */
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<User>();

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
