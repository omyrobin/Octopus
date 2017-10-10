package com.jinying.octopus.bookdetails.catalogue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.jinying.octopus.BaseFragment;
import com.jinying.octopus.R;
import com.jinying.octopus.bean.ChapterAllBean;
import com.jinying.octopus.bean.ChapterBean;
import com.jinying.octopus.bean.StoryVoBean;
import com.jinying.octopus.bean.VolumeAllBean;
import com.jinying.octopus.bean.VolumeBean;
import com.jinying.octopus.read.NovelChapterCache;
import com.jinying.octopus.read.ui.ReadActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by omyrobin on 2017/8/28.
 */

public class CatalogueFragment extends BaseFragment implements View.OnClickListener,ExpandableListView.OnGroupClickListener,ExpandableListView.OnChildClickListener {

    @BindView(R.id.elv_catalogue)
    ExpandableListView mCatalogueELv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView toolbar_title;
    private StoryVoBean mStoryInfo;

    private ChapterAllBean mChapterAllEntity;

    private VolumeAllBean mVolumeAllEntity;

    private ArrayList<VolumeBean> mVolumeList;

    private SectionAdatper adapter;

    public static CatalogueFragment newInstance(StoryVoBean mStoryInfo, ChapterAllBean mChapterAllEntity,VolumeAllBean mVolumeAllEntity){
        CatalogueFragment mChapterListFragment = new CatalogueFragment();
        Bundle args = new Bundle();
        args.putSerializable("StoryInfoEntity", mStoryInfo);
        args.putSerializable("ChapterAllEntity", mChapterAllEntity);
        args.putSerializable("VolumeAllEntity", mVolumeAllEntity);
        mChapterListFragment.setArguments(args);
        return mChapterListFragment;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_catalogue;
    }

    @Override
    protected void obtainArguments() {
        super.obtainArguments();
        mStoryInfo = (StoryVoBean) getArguments().getSerializable("StoryInfoEntity");
        mChapterAllEntity = (ChapterAllBean) getArguments().getSerializable("ChapterAllEntity");
        mVolumeAllEntity = (VolumeAllBean) getArguments().getSerializable("VolumeAllEntity");

        mVolumeList = mVolumeAllEntity.getVolumeList();
    }

    @Override
    protected void initializeToolbar() {
        super.initializeToolbar();
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.btn_back);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText("目录");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
    }

    @Override
    protected void initializeView() {
        initView();
        getList();
    }


    private void initView(){
        mCatalogueELv.setGroupIndicator(null);
        mCatalogueELv.setOnGroupClickListener(this);
        mCatalogueELv.setOnChildClickListener(this);
    }

    private void getList() {
        adapter = new SectionAdatper(getActivity(),mVolumeList);
        adapter.setNovelNameAndId(mStoryInfo.getName()+mStoryInfo.getId());
        mCatalogueELv.setAdapter(adapter);
        for(int i = 0; i < adapter.getGroupCount(); i++){
            mCatalogueELv.expandGroup(i);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        getFragmentManager().popBackStack();
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {
        int position = mVolumeList.get(groupPosition).getChapterList().get(childPosition).getSortNo() - 1;
        NovelChapterCache.getCacheInstance().setmChapterAllEntity(mChapterAllEntity);
        NovelChapterCache.getCacheInstance().setmVolumeAllEntity(mVolumeAllEntity);
        ReadActivity.newInstance(getActivity(), mStoryInfo, position);
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}