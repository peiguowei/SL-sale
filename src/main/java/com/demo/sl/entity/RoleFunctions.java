package com.demo.sl.entity;

import java.util.List;

/**
 * 权限管理 菜单列表实体类
 */
public class RoleFunctions extends Base{
    private Function mainFunction;//主功能
    private List<Function> subFunctionList;//子功能

    public Function getMainFunction() {
        return mainFunction;
    }

    public void setMainFunction(Function mainFunction) {
        this.mainFunction = mainFunction;
    }

    public List<Function> getSubFunctionList() {
        return subFunctionList;
    }

    public void setSubFunctionList(List<Function> subFunctionList) {
        this.subFunctionList = subFunctionList;
    }
}
