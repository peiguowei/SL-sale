package com.demo.sl.entity;

/**
 *基础类  entity包中的所有类共有 都继承它
 */
public class Base {
    private Integer id;
    private Integer startNum;//开始的行数 limit分页从0开始
    private Integer pageSize;//每行显示几个

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartNum() {
        return startNum;
    }

    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
