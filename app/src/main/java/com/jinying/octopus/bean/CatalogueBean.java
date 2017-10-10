package com.jinying.octopus.bean;

import java.util.ArrayList;

/**
 * Created by omyrobin on 2017/8/24.
 */

public class CatalogueBean {

    private ArrayList<VolumeBean> volumeList;

    private ArrayList<ChapterBean> chapterList;

    public ArrayList<VolumeBean> getVolumeList() {
        return volumeList;
    }

    public void setVolumeList(ArrayList<VolumeBean> volumeList) {
        this.volumeList = volumeList;
    }

    public ArrayList<ChapterBean> getChapterList() {
        return chapterList;
    }

    public void setChapterList(ArrayList<ChapterBean> chapterList) {
        this.chapterList = chapterList;
    }
}
