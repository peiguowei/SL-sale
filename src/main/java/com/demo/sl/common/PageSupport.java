package com.demo.sl.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 关于分页的工具
 */
public class PageSupport {
    private Integer pageCount;//总页数
    private Integer totalCount=0;//总行数
    private Integer page=1;//当前页
    private Integer pageSize=1;//一页显示的行数
    private Integer num=3;//当前页前后显示的个数
    private List items=new ArrayList();//当前页面内容的集合

    /**
     * 得到总页数
     * @return
     */
    public Integer getPageCount() {
        return pageCount;
    }

    /**
     * 得到总行数 总个数
     * @return
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总行数 计算出总页数
     * @param totalCount
     */
    public void setTotalCount(Integer totalCount) {
        if (totalCount>0){
            this.totalCount = totalCount;
            this.pageCount=(totalCount+pageSize-1)/pageSize;
        }
    }

    /**
     * 当前页
     * @return
     */
    public Integer getPage() {
        return page;
    }

    /**
     * 设置当前页
     * @param page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * 得到每页总行数
     * @return
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页显示的行数
     * @param pageSize
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 得到当前页前后显示的个数
     * @return
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置当前页前后显示的个数
     * @param num
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 得到当前内容的集合
     * @return
     */
    public List getItems() {
        return items;
    }

    /**
     * 设置当前页内容的集合
     * @param items
     */
    public void setItems(List items) {
        this.items = items;
    }

    /**
     * 得到前一页
     * @return
     */
    public Integer getPrev(){
        return page-1;
    }

    /**
     * 得到后一页
     * @return
     */
    public Integer getNext(){
        return page+1;
    }

    /**
     * 得到最后一页
     * @return
     */
    public Integer getLast(){
        return pageCount;
    }

    /**
     * 判断是否有前一页
     * @return
     */
    public boolean getIsPrev(){
        if (page>1){
            return true;
        }
        return false;
    }

    /**
     * 判断是否有后一页
     * @return
     */
    public boolean getIsNext(){
        if (pageCount!=null&&page<pageCount){
            return true;
        }
        return false;
    }

    /**
     * 得到当前页前num条数据 12345
     * @return
     */
    public List<Integer> getPrevPages(){
        List<Integer> list=new ArrayList();
        Integer _firstPage=1;
        if (page>num){
            _firstPage=page-num;
        }
        for (Integer i=_firstPage;i<page;i++){
            list.add(i);
        }
        return list;
    }
    /**
     * 得到当前页后num条数据
     * @return
     */
    public List<Integer> getNextPages(){
        List<Integer> list=new ArrayList();
        Integer _endPage=num;
        if (pageCount!=null){
            if (num<pageCount&&(page+num)<pageCount){
                _endPage=page+_endPage;
            }else {
                _endPage=pageCount;
            }
        }
        for (Integer i=page+1;i<=_endPage;i++){
            list.add(i);
        }
        return list;
    }

}
