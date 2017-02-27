package com.demo.sl.entity;

import java.util.List;

/**
 *菜单实体类
 */
public class Menu {
    private Function mainMenu;//主菜单
    private List<Function>subMenu;//子菜单 是一个集合列表

    public Function getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(Function mainMenu) {
        this.mainMenu = mainMenu;
    }

    public List<Function> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<Function> subMenu) {
        this.subMenu = subMenu;
    }
}