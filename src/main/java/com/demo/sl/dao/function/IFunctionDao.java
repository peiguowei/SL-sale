package com.demo.sl.dao.function;

import com.demo.sl.entity.Authority;
import com.demo.sl.entity.Function;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *功能接口  有关功能的增删查改
 */
public interface IFunctionDao {
    /**
     * 得到主功能列表
     * @param authority 得到主功能列表的条件 条件：roleId-->functionId+parentId-->主菜单列表
     * @return 返回功能列表集合
     */
    List<Function> getMainFunction(Authority authority);

    /**
     * 得到子功能列表
     * @param function 得到子功能列表的条件 条件：roleId-->functionId+parentId(某一个主功能菜单的id)
     * @return 返回子功能列表集合
     */
    List<Function> getSubFunction(Function function);

    /**
     * 得到主子功能列表
     * @param function 条件 parentId
     * @return 返回
     */
    List<Function> getFunctionList(Function function);

    /**
     * 根据in 条件得到需要的功能列表
     * @param strs 条件 一串字符串
     * @return
     */
    List<Function> getFunctionListByIn(@RequestParam("strs") String strs);

    /**
     * 通过roleId得到功能列表 两张表 权限与功能
     * @param authority 条件
     * @return
     */
    List<Function> getFunctionListByRoleId(Authority authority);
}
