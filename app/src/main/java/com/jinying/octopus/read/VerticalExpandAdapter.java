package com.jinying.octopus.read;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.jinying.octopus.R;
import com.jinying.octopus.bean.ChapterBean;
import com.jinying.octopus.read.persenter.ReadPersenter;

public class VerticalExpandAdapter extends BaseExpandableListAdapter{
	
	public static final int VIEW_TYPE_CONTENT = 0;
	
	public static final int VIEW_TYPE_PAY = 1;
	
	private ArrayList<ArrayList<ChapterBean>> datasArray;
	
	private Context context;

	private int txtSize;

	private int txtColor;
	
	private int lineHeight;

	private String goldAmount = "0";

	private String giveAmount = "0";
	
	public int currIndex;

	private ReadPersenter mPresenter;

	
	public VerticalExpandAdapter(ArrayList<ArrayList<ChapterBean>> datasArray, Context context, ReadPersenter mPresenter) {
		this.datasArray = datasArray;
		this.context = context;
		this.mPresenter = mPresenter;
	}

	@Override
	public int getGroupCount() {
		return datasArray!=null?datasArray.size():0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return datasArray.get(groupPosition).size();
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
	public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
		if(convertView == null ){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_group_layout, parent, false);
		}
		return convertView;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
		int viewType = getChildType(groupPosition, childPosition);
		switch (viewType) {
		case VIEW_TYPE_CONTENT:
			ContentHolder ch;
			if(convertView == null){
				ch = new ContentHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.item_content_layout, parent, false);
				ch.mNovelContentTv = (TextView) convertView.findViewById(R.id.mNovelContentTv);
				convertView.setTag(ch);
			}
			ch = (ContentHolder) convertView.getTag();
			ch.mNovelContentTv.setTextSize(txtSize);
			ch.mNovelContentTv.setTextColor(context.getResources().getColor(txtColor));
			ch.mNovelContentTv.setLineSpacing(10f, 1.0f);
			ch.mNovelContentTv.setText(datasArray.get(groupPosition).get(childPosition).getContent());
			break;
		}
		return convertView;
	}
	
	@Override
	public int getChildType(int groupPosition, int childPosition) {
		return VIEW_TYPE_CONTENT;
	}

	@Override
	public int getChildTypeCount() {
		return 2;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	static class groupHolder{
		View view;
	}
	
	static class ContentHolder{
		public TextView mNovelContentTv;
	}

	public void refreshData(ArrayList<ArrayList<ChapterBean>> datasArray) {
		this.datasArray = datasArray;
		notifyDataSetChanged();
	}
	
	public void addPreDatas(ArrayList<ChapterBean> datas){
		if(!datasArray.contains(datas)){
			datasArray.add(0, datas);
			notifyDataSetChanged();
		}
	}
	
	public void addNextDatas(ArrayList<ChapterBean> datas){
		if(!datasArray.contains(datas)){
			datasArray.add(datas);
			notifyDataSetChanged();
		}
	}
	
	public ArrayList<ArrayList<ChapterBean>> getDatas() {
		return this.datasArray;
	}
	
	public void setTextSize(int txtSize) {
		this.txtSize = txtSize;
	}
	
	public void setTextColor(int txtColor) {
		this.txtColor = txtColor;
	}

	public void setLineHeight(int lineHeight) {
		this.lineHeight = lineHeight;
	}
	
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		mPresenter.onExpandGroup();
	}
}
