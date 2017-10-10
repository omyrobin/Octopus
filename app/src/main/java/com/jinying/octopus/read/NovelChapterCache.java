package com.jinying.octopus.read;

import com.jinying.octopus.bean.ChapterAllBean;
import com.jinying.octopus.bean.VolumeAllBean;

public class NovelChapterCache {
	
	public static volatile NovelChapterCache mCache;
	
	public VolumeAllBean mVolumeAllEntity;
	
	public ChapterAllBean mChapterAllEntity;
	
	public static NovelChapterCache getCacheInstance(){
		if(mCache == null){
			synchronized (NovelChapterCache.class) {
				mCache = new NovelChapterCache();
			}
		}
		return mCache;
	}

	public VolumeAllBean getmVolumeAllEntity() {
		return mVolumeAllEntity;
	}

	public void setmVolumeAllEntity(VolumeAllBean mVolumeAllEntity) {
		this.mVolumeAllEntity = mVolumeAllEntity;
	}

	public ChapterAllBean getmChapterAllEntity() {
		return mChapterAllEntity;
	}

	public void setmChapterAllEntity(ChapterAllBean mChapterAllEntity) {
		this.mChapterAllEntity = mChapterAllEntity;
	}
}
