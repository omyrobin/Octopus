package com.jinying.octopus.bean;

/**
 * Created by omyrobin on 2017/8/23.
 */

public class SearchAllBean {
    private long id;
    private long novelColumn;
    private long updateTime;
    private long updateAdminId;
    private String updateAdminName;
    private int isDelete;
    private String keyWord;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNovelColumn() {
        return novelColumn;
    }

    public void setNovelColumn(long novelColumn) {
        this.novelColumn = novelColumn;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getUpdateAdminId() {
        return updateAdminId;
    }

    public void setUpdateAdminId(long updateAdminId) {
        this.updateAdminId = updateAdminId;
    }

    public String getUpdateAdminName() {
        return updateAdminName;
    }

    public void setUpdateAdminName(String updateAdminName) {
        this.updateAdminName = updateAdminName;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
