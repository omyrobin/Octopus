package com.jinying.octopus.event;

import java.util.ArrayList;

import com.jinying.octopus.bean.ChapterBean;
import com.jinying.octopus.bean.VolumeBean;

public class EventChapterList {

	private String id;

	private ArrayList<ChapterBean> chapterList;

	private ArrayList<VolumeBean> volumeList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<ChapterBean> getChapterList() {
		return chapterList;
	}

	public void setChapterList(ArrayList<ChapterBean> chapterList) {
		this.chapterList = chapterList;
	}

	public ArrayList<VolumeBean> getVolumeList() {
		return volumeList;
	}

	public void setVolumeList(ArrayList<VolumeBean> volumeList) {
		this.volumeList = volumeList;
	}
}
