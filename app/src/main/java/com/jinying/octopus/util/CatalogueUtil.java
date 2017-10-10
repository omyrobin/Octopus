package com.jinying.octopus.util;

import com.jinying.octopus.bean.ChapterAllBean;
import com.jinying.octopus.bean.ChapterBean;
import com.jinying.octopus.bean.VolumeAllBean;
import com.jinying.octopus.bean.VolumeBean;
import com.jinying.octopus.read.NovelChapterCache;

import java.util.ArrayList;

public class CatalogueUtil {
	
	public static ArrayList<VolumeBean> getVolumeList(ArrayList<VolumeBean> mVolumeList, ArrayList<ChapterBean> mChapterList){
		for (int i = 0; i < mVolumeList.size(); i++) {
			mVolumeList.get(i).setChapterList(new ArrayList<ChapterBean>());
			for (int j = 0; j < mChapterList.size(); j++) {
				if(mChapterList.get(j).getVolumeId().equals(mVolumeList.get(i).getId())) {
					mChapterList.get(j).setGroupId(i);
					mChapterList.get(j).setChildId(j);
					mVolumeList.get(i).getChapterList().add(mChapterList.get(j));
				}
			}
		}
		return mVolumeList;
	}

	public static void addToNovelChapterCache(ArrayList<VolumeBean> volumeBeanList, ArrayList<ChapterBean> mChapterList){
		VolumeAllBean vallBean = new VolumeAllBean();
		vallBean.setVolumeList(volumeBeanList);
		ChapterAllBean callBean = new ChapterAllBean();
		callBean.setChapterList(mChapterList);
		NovelChapterCache.getCacheInstance().setmVolumeAllEntity(vallBean);
		NovelChapterCache.getCacheInstance().setmChapterAllEntity(callBean);
	}
}
