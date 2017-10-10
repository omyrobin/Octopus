package com.jinying.octopus.bean;

import java.io.Serializable;

/**
 * Created by omyrobin on 2017/8/24.
 */

public class ChapterBean implements Serializable{
    /**
     * id : 1000358240
     * storyId : 1000497
     * volumeId : 10000209
     * userId : 1000000106
     * name : 第五百一十六章 王家三少爷
     * wordNumber : 3307
     * price : 17
     * isPay : 0
     * createDate : 1492685468179
     * updateDate : 1501481036681
     * publishTime : 1492768800000
     * payed : false
     * sortNo : 517
     */

    private String id;
    private String storyId;
    private String volumeId;
    private String userId;
    private String name;
    private int wordNumber;
    private int price;
    private int isPay;
    private long createDate;
    private long updateDate;
    private long publishTime;
    private boolean payed;
    private int sortNo;

    private int groupId;
    private int childId;
    private String content;
    private int readCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWordNumber() {
        return wordNumber;
    }

    public void setWordNumber(int wordNumber) {
        this.wordNumber = wordNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
