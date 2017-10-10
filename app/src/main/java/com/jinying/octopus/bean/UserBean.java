package com.jinying.octopus.bean;

import java.io.Serializable;

/**
 * Created by omyrobin on 2017/8/21.
 */

public class UserBean implements Serializable {

    private String userId;
    private String nickName;
    private String facePic;
    private String sex;
    private int isBinding;
    private int autoPayChapter;
    private String state;
    private String businessInfo;
    private String mobilephone;
    private String tinyFacePic;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFacePic() {
        return facePic;
    }

    public void setFacePic(String facePic) {
        this.facePic = facePic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getIsBinding() {
        return isBinding;
    }

    public void setIsBinding(int isBinding) {
        this.isBinding = isBinding;
    }

    public int getAutoPayChapter() {
        return autoPayChapter;
    }

    public void setAutoPayChapter(int autoPayChapter) {
        this.autoPayChapter = autoPayChapter;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(String businessInfo) {
        this.businessInfo = businessInfo;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getTinyFacePic() {
        return tinyFacePic;
    }

    public void setTinyFacePic(String tinyFacePic) {
        this.tinyFacePic = tinyFacePic;
    }

}
