package com.jinying.octopus.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by omyrobin on 2017/8/23.
 */

public class SearchBookBean implements Serializable{

    private int pageNo;// 页号
    private int pageSize;// 每页数据数量
    private String sort; // 排序字段
    private int total; // 总数据数量
    private int totalPages; // 总页数
    private ArrayList<StoryVoBean> list;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<StoryVoBean> getList() {
        return list;
    }

    public void setList(ArrayList<StoryVoBean> list) {
        this.list = list;
    }
}
