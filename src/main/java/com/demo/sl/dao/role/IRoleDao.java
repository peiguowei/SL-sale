package com.demo.sl.dao.role;

import com.demo.sl.entity.Role;
import com.demo.sl.entity.User;

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

    /**
     * 只得到启用的角色
     * @return 非null 角色表的集合
     */
    List<Role> getRoleListByIsStart();

    /**
     * 根据条件获得角色信息表的数据
     * @param role 条件
     * @return
     */
    Role getRoleBy(Role role);

    /**
     * 添加角色
     * @param role 待添加的内容
     */
    void addRole(Role role);

    /**
     * 根据条件删除角色
     * @param role 条件
     */
    void deleteRole(Role role);

    /**
     * 根据条件修改角色
     * @param role 条件
     */
    void modifyRole(Role role);
}
