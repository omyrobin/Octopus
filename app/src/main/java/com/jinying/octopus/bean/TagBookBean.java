package com.jinying.octopus.bean;

import java.util.List;

/**
 * Created by omyrobin on 2017/8/25.
 */

public class TagBookBean {

    /**
     * pageNo : 1
     * pageSize : 10
     * sort : null
     * total : 16
     * totalPages : 2
     */

    private int pageNo;
    private int pageSize;
    private Object sort;
    private int total;
    private int totalPages;
    private List<ListBean> list;

    public static class ListBean {
        private String name;//小说名称
        private String channel;//小说频道
        private String pcIntroduce;//小说PC简介
        private String cover;//小说封面
        private String introduce;//小说简介
        private String appIntroduce;//小说APP简介
        private String author;//作者名称
        private String type;//分类
        private String tag;//标签
        private Long pvAll = 0L;//浏览次数
        private Long pvLast3Day = 0L;//浏览次数最近3天
        private Long id;//小说id

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getPcIntroduce() {
            return pcIntroduce;
        }

        public void setPcIntroduce(String pcIntroduce) {
            this.pcIntroduce = pcIntroduce;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getAppIntroduce() {
            return appIntroduce;
        }

        public void setAppIntroduce(String appIntroduce) {
            this.appIntroduce = appIntroduce;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public Long getPvAll() {
            return pvAll;
        }

        public void setPvAll(Long pvAll) {
            this.pvAll = pvAll;
        }

        public Long getPvLast3Day() {
            return pvLast3Day;
        }

        public void setPvLast3Day(Long pvLast3Day) {
            this.pvLast3Day = pvLast3Day;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

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

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }
}
