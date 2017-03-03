package com.demo.sl.service.role;

import com.demo.sl.dao.role.IRoleDao;
import com.demo.sl.entity.Role;
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

    /**
     * 业务 得到角色表的所有信息
     * @return 非null角色表的所有信息
     */
    public List<Role> getRoleListService(){
        return roleDao.getRoleList();
    }
}
