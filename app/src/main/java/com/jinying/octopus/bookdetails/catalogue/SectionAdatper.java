package com.jinying.octopus.bookdetails.catalogue;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinying.octopus.R;
import com.jinying.octopus.bean.ChapterBean;
import com.jinying.octopus.bean.VolumeBean;
import com.jinying.octopus.util.FileUtil;

import java.util.ArrayList;

public class SectionAdatper extends BaseExpandableListAdapter{

	private boolean isReadActivity;
	
	private int currIndex;

	private String novelNameAndId;
	
	private Context context;
	
	private ArrayList<VolumeBean> mVolumeList;

	private int bgIndex;

	private boolean isFree;
	
	public SectionAdatper(Context context,ArrayList<VolumeBean> mVolumeList) {
		super();
		this.context = context;
		this.mVolumeList = mVolumeList;
	}

	@Override
	public int getGroupCount() {
		return mVolumeList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mVolumeList.get(groupPosition).getChapterList().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		SectionHeaderHolder headerHolder;
		if(convertView==null){
			headerHolder = new SectionHeaderHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_item_section_header, parent, false);
			headerHolder.mSectionHeaderTv = (TextView) convertView.findViewById(R.id.mSectionHeaderTv);
			headerHolder.mSectionHeaderLlyt = (LinearLayout) convertView.findViewById(R.id.mSectionHeaderLlyt);
			convertView.setTag(headerHolder);
		}
		headerHolder = (SectionHeaderHolder) convertView.getTag();
		headerHolder.mSectionHeaderTv.setText(mVolumeList.get(groupPosition).getName());
		//根据背景色来设置相关UI颜色
		if(bgIndex == 4){
			headerHolder.mSectionHeaderLlyt.setBackgroundColor(ContextCompat.getColor(context, R.color.night_txt_bg));
			headerHolder.mSectionHeaderTv.setTextColor(ContextCompat.getColor(context, R.color.textColorNight));
		}else{
			headerHolder.mSectionHeaderTv.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary));
		}
		return convertView;
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		SectionItemHolder childHolder;
		if(convertView == null){
			childHolder = new SectionItemHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_chapterlist, parent, false);
			childHolder.mChapterListRlyt = (RelativeLayout) convertView.findViewById(R.id.mChapterListRlyt);
			childHolder.mChapterListNumTv = (TextView) convertView.findViewById(R.id.mChapterListNumTv);
			childHolder.mChapterListDivider = convertView.findViewById(R.id.mChapterListDivider);
			convertView.setTag(childHolder);
		}
		childHolder = (SectionItemHolder) convertView.getTag();
		ChapterBean c = mVolumeList.get(groupPosition).getChapterList().get(childPosition);
		//根据背景色来设置相关UI颜色
		if(bgIndex == 4){
			childHolder.mChapterListRlyt.setBackgroundColor(ContextCompat.getColor(context, R.color.night_txt_bg));
			childHolder.mChapterListDivider.setBackgroundResource(R.color.colorDivider);
			if(FileUtil.isHaveNovelChapterTxt(novelNameAndId, c.getId())){
				childHolder.mChapterListNumTv.setTextColor(ContextCompat.getColor(context, R.color.textColorNight));
			}
			else{
				childHolder.mChapterListNumTv.setTextColor(ContextCompat.getColor(context, R.color.textColorSecondary));
			}
		}else{
			if(FileUtil.isHaveNovelChapterTxt(novelNameAndId, c.getId())){
				childHolder.mChapterListNumTv.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary));
			}
			else{
				childHolder.mChapterListNumTv.setTextColor(ContextCompat.getColor(context, R.color.textColorSecondary));
			}
		}
		if(isReadActivity && c.getSortNo()-1 == currIndex){
			childHolder.mChapterListNumTv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
		}
		childHolder.mChapterListNumTv.setText(c.getName());
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	public int getCurrIndex() {
		return currIndex;
	}

	public void setCurrIndex(int currIndex) {
		this.currIndex = currIndex;
	}
	
	public boolean isReadActivity() {
		return isReadActivity;
	}

	public void setReadActivity(boolean isReadActivity) {
		this.isReadActivity = isReadActivity;
	}

	public String getNovelNameAndId() {
		return novelNameAndId;
	}

	public void setNovelNameAndId(String novelNameAndId) {
		this.novelNameAndId = novelNameAndId;
	}

	public ArrayList<VolumeBean> getmVolumeList() {
		return mVolumeList;
	}

	public void setmVolumeList(ArrayList<VolumeBean> mVolumeList) {
		this.mVolumeList = mVolumeList;
		notifyDataSetChanged();
	}

	class SectionHeaderHolder{
		private LinearLayout mSectionHeaderLlyt;
		private TextView mSectionHeaderTv;

	}
	
	class SectionItemHolder{
		private View mChapterListDivider;
		private RelativeLayout mChapterListRlyt;
		private TextView mChapterListNumTv;
		
	}

	public void setBgIndex(int bgIndex) {
		this.bgIndex = bgIndex;
	}
	
}
