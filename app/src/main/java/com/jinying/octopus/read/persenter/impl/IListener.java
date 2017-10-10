package com.jinying.octopus.read.persenter.impl;

public interface IListener {
	
	void onContentSuccess(String content, int which, String chapterId, int mode);
	
	void onContentFail();
	
	void showNovelDialog();

}
