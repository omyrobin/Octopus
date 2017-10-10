package com.jinying.octopus.read;

import java.util.ArrayList;

import com.jinying.octopus.bean.ChapterBean;
import com.jinying.octopus.bean.VolumeBean;

public class NovelChapterBean {

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
