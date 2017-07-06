package com.demo.sl.service.role;

import com.demo.sl.dao.role.IRoleDao;
import com.demo.sl.dao.user.IUserDao;
import com.demo.sl.entity.Role;
import com.demo.sl.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色业务层
 */
@Service
public class RoleService {
    @Resource
    private IRoleDao roleDao;
    @Resource
    private IUserDao userDao;

    /**
     * 业务 得到角色表的所有信息
     * @return 非null角色表的所有信息
     */
    public List<Role> getRoleListService(){
        return roleDao.getRoleList();
    }

    /**
     * 业务 得到启用的角色所有信息
     * @return 非null 启用的角色的所有信息
     */
    public List<Role> getRoleListOfStartService(){
        return roleDao.getRoleListByIsStart();
    }

    /**
     * 业务 得到role表的信息通过条件
     * @param role 条件
     * @return
     */
    public Role getRoleByServices(Role role){
        return roleDao.getRoleBy(role);
    }

    /**
     *业务 添加角色
     * @param role 待添加的内容
     */
    public void addRoleService(Role role){
         roleDao.addRole(role);
    }

    /**
     * 业务 删除角色
     * @param role 条件
     */
    public void deleteRoleService(Role role){
         roleDao.deleteRole(role);
    }

    /**
     * 业务 修改角色与启用角色 aop 面型切面编程
     * @param role 条件
     */
    public void pz_modifyRoleService(Role role){
        roleDao.modifyRole(role);
//        角色改变 用户表也要改变
        Integer roleId = role.getId();//角色id
        String roleName = role.getRoleName();//角色名称
        User user = new User();
        user.setRoleId(roleId);
        user.setRoleName(roleName);
//        面向切面 调用user业务更改角色
        userDao.modifyUserForRole(user);
    }
}
