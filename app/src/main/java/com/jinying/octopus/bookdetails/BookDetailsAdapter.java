package com.jinying.octopus.bookdetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.util.Preconditions;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinying.octopus.App;
import com.jinying.octopus.R;
import com.jinying.octopus.bean.CatalogueBean;
import com.jinying.octopus.bean.ChapterAllBean;
import com.jinying.octopus.bean.StoryListBean;
import com.jinying.octopus.bean.StoryVoBean;
import com.jinying.octopus.bean.VolumeAllBean;
import com.jinying.octopus.bookdetails.catalogue.CatalogueFragment;
import com.jinying.octopus.holder.BookViewHolder;
import com.jinying.octopus.image.ImageLoader;
import com.jinying.octopus.util.CatalogueUtil;
import com.jinying.octopus.util.DateUtil;
import com.jinying.octopus.util.DensityUtils;
import com.jinying.octopus.util.TagLayoutUtils;
import com.jinying.octopus.view.TagLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by omyrobin on 2017/8/24.
 */

public class BookDetailsAdapter extends RecyclerView.Adapter {

    private Context context;

    private StoryVoBean bookDetails;

    private CatalogueBean catalogueBean;

    public ArrayList<StoryListBean> recommedStory;

    private int count;

    public static final int BOOK = 0;

    public static final int CATALOGUE = 1;

    public static final int RECOMMEND = 2;

    private TagLayoutUtils mUtils;
    @NonNull
    private BookDetailsContract.Presenter mPresenter;

    public BookDetailsAdapter(Context context, @NonNull BookDetailsContract.Presenter mPresenter) {
        this.mPresenter = Preconditions.checkNotNull(mPresenter);
        this.context = context;
        mUtils = new TagLayoutUtils(context, null);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0){
            View itemView = LayoutInflater.from(context).inflate(R.layout.adapter_item_bookdetails,parent, false);
            return new DetaislViewHolder(itemView);
        }else if(viewType == 1){
            View itemView = LayoutInflater.from(context).inflate(R.layout.adapter_item_bookdetails_chapter,parent, false);
            return new ChapterViewHolder(itemView);
        }else{
            View itemView = LayoutInflater.from(context).inflate(R.layout.adapter_item_bookdetails_recommend,parent, false);
            return new RecommendViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType){
            case BOOK:
                ((DetaislViewHolder)holder).mBookNameTv.setText(bookDetails.getName());
                ((DetaislViewHolder)holder).mBookActorTv.setText(bookDetails.getAuthorName() +" | " + bookDetails.getType());
                if(!TextUtils.isEmpty(bookDetails.getIntroduce())){
                    String [] introduces = bookDetails.getIntroduce().trim().split("\n");
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < introduces.length; i++) {
                        introduces[i] = "\u3000\u3000"+introduces[i].replaceAll("\\s*", "")+"\n";
                        sb.append(introduces[i]);
                    }
                    ((DetaislViewHolder)holder).mBookBriefTv.setText(sb.toString());
                    mPresenter.getDescriptionTvLineCount(((DetaislViewHolder)holder).mBookBriefTv);

                    ((DetaislViewHolder)holder).mBookBriefTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mPresenter.isUnfold((TextView) view);
                        }
                    });
                }
                ((DetaislViewHolder)holder).mBookDetailsUpDateTv.setText(context.getString(R.string.updated) + DateUtil.time2Tip(bookDetails.getLatesetChapterTimeStr()));
                ((DetaislViewHolder)holder).mBookDetailsReadCount.setText(bookDetails.getPv() + "人次");
                ImageLoader.loadNovelCover(context,bookDetails.getCover(),((DetaislViewHolder)holder).mBookCoverImg);
                ((DetaislViewHolder)holder).mBookDetailsTl.removeAllViews();
                for (String lable : bookDetails.getTagList()) {
                    if(!TextUtils.isEmpty(lable)){
                        mUtils.addToTagLayout(lable, R.color.colorAccent, R.drawable.shape_search_tag_blue,((DetaislViewHolder)holder).mBookDetailsTl);
                    }
                }
                break;

            case CATALOGUE:
                ((ChapterViewHolder)holder).mBookChpterNumber.setText(String.format(context.getString(R.string.serialize), bookDetails.getLatestChapter()));
                ((ChapterViewHolder)holder).mBookChpterUpdateTime.setText(context.getString(R.string.updated) + DateUtil.time2Tip(bookDetails.getLatesetChapterTimeStr()));
                ((ChapterViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(catalogueBean.getVolumeList()==null || catalogueBean.getChapterList()==null){
                            return;
                        }
                        ChapterAllBean chapterAllBean = new ChapterAllBean();
                        chapterAllBean.setChapterList(catalogueBean.getChapterList());
                        VolumeAllBean volumeAllBean = new VolumeAllBean();
                        volumeAllBean.setVolumeList(CatalogueUtil.getVolumeList(catalogueBean.getVolumeList(), catalogueBean.getChapterList()));
                        CatalogueFragment mChapterListFragment = CatalogueFragment.newInstance(bookDetails,chapterAllBean ,volumeAllBean);
                        ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction()
                                .add(android.R.id.content, mChapterListFragment)
                                .addToBackStack(null)
                                .show(mChapterListFragment)
                                .commit();
                    }
                });
                break;

            default:
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 4; j++) {
                        if(count > recommedStory.size()-1){
                            return;
                        }
                        View childView = LayoutInflater.from(context).inflate(R.layout.adapter_item_bookdetails_gridlayout, null);
                        ImageView mBookCoverImg = childView.findViewById(R.id.img_book_cover);
                        TextView mBookNameTv = childView.findViewById(R.id.tv_book_name);
                        childView.setId(count);
                        ImageLoader.loadNovelCover(context,recommedStory.get(count).getStory().getCover(),mBookCoverImg);
                        mBookNameTv.setText(recommedStory.get(count).getStory().getName());

                        GridLayout.Spec rowSpec = GridLayout.spec(i);
                        final GridLayout.Spec columnSpec = GridLayout.spec(j);
                        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec,columnSpec);

                        ((RecommendViewHolder)holder).mBookDetailsListGl.setUseDefaultMargins(true);
                        params.width = (int) (App.width/4 - DensityUtils.dp2px(context, 10));
                        params.height = (int) (App.width/2 - DensityUtils.dp2px(context, 30));
                        ((RecommendViewHolder)holder).mBookDetailsListGl.addView(childView, params);

                        childView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mPresenter.toBookDetailsActivity(recommedStory.get(view.getId()).getStory().getId());
                            }
                        });
                        count ++;
                    }
                }
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return BOOK;
        }else if(position == 1){
            return CATALOGUE;
        }else{
            return RECOMMEND;
        }
    }

    @Override
    public int getItemCount() {
        if(recommedStory != null && bookDetails != null && catalogueBean != null){
            return 3;
        }
        if (bookDetails != null && catalogueBean != null){
            return 2;
        }
        if(bookDetails != null){
            return 1;
        }
        return 0;
    }

    public void setBookDetails(StoryVoBean bookDetails) {
        this.bookDetails = bookDetails;
        notifyDataSetChanged();
    }

    public void setCatalogue(CatalogueBean catalogueBean) {
        this.catalogueBean = catalogueBean;
        notifyDataSetChanged();
    }

    public void setRecommedStory(ArrayList<StoryListBean> recommedStory) {
        this.recommedStory = recommedStory;
        notifyDataSetChanged();
    }

    static class DetaislViewHolder extends BookViewHolder{

        @BindView(R.id.tl_bookdetails_tag)
        TagLayout mBookDetailsTl;
        @BindView(R.id.tv_book_update)
        TextView mBookDetailsUpDateTv;
        @BindView(R.id.tv_book_readcount)
        TextView mBookDetailsReadCount;
        public DetaislViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class ChapterViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_book_chapter_number)
        TextView mBookChpterNumber;
        @BindView(R.id.tv_book_chapter_updatetime)
        TextView mBookChpterUpdateTime;
        public ChapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    static class RecommendViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.gl_bookdetails_like)
        GridLayout mBookDetailsListGl;
        public RecommendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
