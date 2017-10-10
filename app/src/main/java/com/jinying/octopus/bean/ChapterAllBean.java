package com.jinying.octopus.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by omyrobin on 2017/8/28.
 */

public class ChapterAllBean implements Serializable{
    private ArrayList<ChapterBean> chapterList;

    public ArrayList<ChapterBean> getChapterList() {
        return chapterList;
    }

    public void setChapterList(ArrayList<ChapterBean> chapterList) {
        this.chapterList = chapterList;
    }
}
