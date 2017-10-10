package com.jinying.octopus.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by omyrobin on 2017/8/28.
 */

public class VolumeAllBean implements Serializable{

    private ArrayList<VolumeBean> volumeList;

    public ArrayList<VolumeBean> getVolumeList() {
        return volumeList;
    }

    public void setVolumeList(ArrayList<VolumeBean> volumeList) {
        this.volumeList = volumeList;
    }
}
