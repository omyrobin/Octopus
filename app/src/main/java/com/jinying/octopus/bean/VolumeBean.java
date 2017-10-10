package com.jinying.octopus.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by omyrobin on 2017/8/24.
 */

public class VolumeBean implements Serializable{

    /**
     * id : 10000209
     * flag : null
     * storyId : 1000497
     * userId : 1000000106
     * name : 卷一
     * introduce :
     * isDelete : 0
     * createDate : 1462945261643
     * latestRevisionDate : 1462945261643
     * groupId : 0
     * chapterInfoList : null
     * idStr : 10000209
     * storyIdStr : 1000497
     */

    private String id;
    private Object flag;
    private String storyId;
    private int userId;
    private String name;
    private String introduce;
    private int isDelete;
    private long createDate;
    private long latestRevisionDate;
    private int groupId;
    private Object chapterInfoList;
    private String idStr;
    private String storyIdStr;
    private ArrayList<ChapterBean> chapterList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getFlag() {
        return flag;
    }

    public void setFlag(Object flag) {
        this.flag = flag;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getLatestRevisionDate() {
        return latestRevisionDate;
    }

    public void setLatestRevisionDate(long latestRevisionDate) {
        this.latestRevisionDate = latestRevisionDate;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public Object getChapterInfoList() {
        return chapterInfoList;
    }

    public void setChapterInfoList(Object chapterInfoList) {
        this.chapterInfoList = chapterInfoList;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public String getStoryIdStr() {
        return storyIdStr;
    }

    public void setStoryIdStr(String storyIdStr) {
        this.storyIdStr = storyIdStr;
    }

    public ArrayList<ChapterBean> getChapterList() {
        return chapterList;
    }

    public void setChapterList(ArrayList<ChapterBean> chapterList) {
        this.chapterList = chapterList;
    }
}
