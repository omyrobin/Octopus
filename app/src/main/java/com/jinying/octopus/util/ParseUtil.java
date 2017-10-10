package com.jinying.octopus.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jinying.octopus.bean.ChapterBean;
import com.jinying.octopus.bean.VolumeBean;

import java.util.ArrayList;
import java.util.List;

public class ParseUtil {

	private static Gson gson = new Gson();
	
	public static <T> T getPerson(String json, Class<T> cls){
		return gson.fromJson(json, cls);
	}

	/**获取小说章节数据**/
	public static ArrayList<ChapterBean> getChapterList(String json){
		return gson.fromJson(json, new TypeToken<List<ChapterBean>>(){}.getType());
	}
	/**获取小说包含卷章节数据**/
	public static ArrayList<VolumeBean> getVolumeList(String json){
		return gson.fromJson(json, new TypeToken<List<VolumeBean>>(){}.getType());
	}
}
