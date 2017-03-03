package com.demo.sl.service.Function;

import com.demo.sl.dao.function.IFunctionDao;
import com.demo.sl.entity.Authority;
import com.demo.sl.entity.Function;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能业务层
 */
@Service
public class FunctionService {
    @Resource
    private IFunctionDao functionDao;

    /**
     * 从数据库中得到主功能菜单信息
     * @param roleId 得到主功能菜单的一个条件 另一个条件是 （主功能）parentId=0
     * @return 非null 主功能菜单的集合列表
     */
    public List<Function>getFunctionMainMenuService(Integer roleId){
        Authority authority=new Authority();//创建权限的实例
        authority.setRoleId(roleId);
        //查询数据库得到主功能菜单列表
        List<Function> mainFunctionMenu = functionDao.getMainFunction(authority);
        return mainFunctionMenu;
    }

    /**
     * 从数据库中得到子功能菜单列表
     * @param function 查询的条件 id roleId
     * @return 非null 子功能菜单的集合列表
     */
    public List<Function> getFunctionSubMenuService(Function function){
        //查询数据库的子功能菜单列表
        List<Function> subFunctionMenu = functionDao.getSubFunction(function);
        return subFunctionMenu;
    }
}
