package com.demo.sl.dao.role;

import com.demo.sl.entity.Role;

import java.util.List;

/**
 * 角色接口 操作角色表 操作数据库
 */
public interface IRoleDao {
    /**
     * 从数据库中的到角色表的信息
     * @return 非null 角色表的集合
     */
    List<Role> getRoleList();
}
