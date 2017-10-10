package com.jinying.octopus.bean;

/**
 * Created by omyrobin on 2017/8/22.
 */

public class BannerBean {

    private long id;
    private int novelColumn;
    private String updateTime;
    private String updateAdminId;
    private String updateAdminName;
    private int isDelete;
    private String pic;
    private String content;
    private String linkUrl;
    private int targetType;//	    0:外联  1:小说  2:专题
    private long targetId;
    private String targetName;
    private int disappear;
    private String targetTypeName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNovelColumn() {
        return novelColumn;
    }

    public void setNovelColumn(int novelColumn) {
        this.novelColumn = novelColumn;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateAdminId() {
        return updateAdminId;
    }

    public void setUpdateAdminId(String updateAdminId) {
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public int getTargetType() {
        return targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public int getDisappear() {
        return disappear;
    }

    public void setDisappear(int disappear) {
        this.disappear = disappear;
    }

    public String getTargetTypeName() {
        return targetTypeName;
    }

    public void setTargetTypeName(String targetTypeName) {
        this.targetTypeName = targetTypeName;
    }

}
