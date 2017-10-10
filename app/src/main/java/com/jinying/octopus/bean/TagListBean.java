package com.jinying.octopus.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by omyrobin on 2017/8/25.
 */

public class TagListBean implements Serializable {
    private List<TagBean> categorys;
    private List<TagBean> tags;
    private List<TagBean> overList;
    private List<TagBean> sortList;
    private int channel;
    public List<TagBean> getCategorys() {
        return categorys;
    }
    public void setCategorys(List<TagBean> categorys) {
        this.categorys = categorys;
    }
    public List<TagBean> getTags() {
        return tags;
    }
    public void setTags(List<TagBean> tags) {
        this.tags = tags;
    }
    public int getChannel() {
        return channel;
    }
    public void setChannel(int channel) {
        this.channel = channel;
    }
    public List<TagBean> getOverList() {
        return overList;
    }
    public void setOverList(List<TagBean> overList) {
        this.overList = overList;
    }
    public List<TagBean> getSortList() {
        return sortList;
    }
    public void setSortList(List<TagBean> sortList) {
        this.sortList = sortList;
    }
}
