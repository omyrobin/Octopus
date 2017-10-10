package com.jinying.octopus.bookshelf;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinying.octopus.App;
import com.jinying.octopus.R;
import com.jinying.octopus.bean.BookShelfBean;
import com.jinying.octopus.bean.StoryVoBean;
import com.jinying.octopus.image.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by omyrobin on 2017/8/21.
 */

public class BookShelfAdapter extends RecyclerView.Adapter{

    private Context context;

    private ArrayList<BookShelfBean> list;

    private View bannerView;

    private BookShelfContract.Presenter mPresenter;

    private boolean isEdit;

    public BookShelfAdapter(Context context, BookShelfContract.Presenter mPresenter) {
        this.context = context;
        this.mPresenter = mPresenter;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return 0;
        }
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0){
            return new BannerViewHolder(bannerView);
        }else{
            View itemView = LayoutInflater.from(context).inflate(R.layout.adapter_item_bookshelf_grid_layout, parent, false);
            AbsListView.LayoutParams param = new AbsListView.LayoutParams((int) (App.width/3),ViewGroup.LayoutParams.WRAP_CONTENT);
            itemView.setLayoutParams(param);
            return new BookViewHolder(itemView);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        if(viewType != 0){
            final StoryVoBean storyVoBean;
            storyVoBean = list.get(position-1).getStoryVo();
            ((BookViewHolder)holder).mBookNameTv.setText(storyVoBean.getName());
            ImageLoader.loadNovelCover(context,storyVoBean.getCover(), ((BookViewHolder)holder).mBookCoverImg);
//            if(list.get(position-1).getUnReadingCount()!=0){
//                int size = list.get(position-1).getUnReadingCount();
//                setBackground(((BookViewHolder)holder).mBookBadgeTv,20, ContextCompat.getColor(context,R.color.colorAccent));
//                ((BookViewHolder)holder).mBookBadgeTv.setVisibility(View.VISIBLE);
//                ((BookViewHolder)holder).mBookBadgeTv.setText(size+"");
//            }else{
//                ((BookViewHolder)holder).mBookBadgeTv.setVisibility(View.INVISIBLE);
//            }
            if(isEdit){
                ((BookViewHolder)holder).mDeleteChk.setChecked(storyVoBean.isChecked());
                ((BookViewHolder)holder).mDeleteChk.setVisibility(View.VISIBLE);
            }else{
                ((BookViewHolder)holder).mDeleteChk.setVisibility(View.INVISIBLE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isEdit){
                        ((BookViewHolder)holder).mDeleteChk.performClick();
                    }else{
                        mPresenter.beginReadBook(list.get(position-1).getStoryVo());
                    }
                }
            });

            //设置CheckBox的点击事件
            ((BookViewHolder)holder).mDeleteChk.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    list.get(position-1).getStoryVo().setChecked(!list.get(position-1).getStoryVo().isChecked());
                    mPresenter.checkedDeleteChk(list.get(position-1).getStoryVo(), list.size());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(bannerView != null){
            return list == null ? 1 : list.size() + 1;
        }
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setBackground(TextView view, int dipRadius, int badgeColor) {
        int radius = dip2Px(dipRadius);
        float[] radiusArray = new float[] { radius, radius, radius, radius, radius, radius, radius, radius };

        RoundRectShape roundRect = new RoundRectShape(radiusArray, null, null);
        ShapeDrawable bgDrawable = new ShapeDrawable(roundRect);
        bgDrawable.getPaint().setColor(badgeColor);
        view.setBackground(bgDrawable);
        view.setTextColor(Color.WHITE);
        view.setTypeface(Typeface.DEFAULT_BOLD);
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        view.setPadding(dip2Px(5), dip2Px(1), dip2Px(5), dip2Px(1));
        view.setGravity(Gravity.CENTER);
    }

    private int dip2Px(float dip) {
        return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
    }


    public void setBannerView(View bannerView) {
        this.bannerView = bannerView;
        notifyDataSetChanged();
    }

    public void setBookList(ArrayList<BookShelfBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void showGridDeleteView() {
        isEdit = true;
        notifyDataSetChanged();
    }

    public void dimissGridDeleteView() {
        isEdit = false;
        notifyDataSetChanged();
    }

    public void deleteAll(boolean isDeleteAll) {
        for (int i = 0; i< list.size(); i++){
            list.get(i).getStoryVo().setChecked(isDeleteAll);
        }
        notifyDataSetChanged();
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder{

        public BannerViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class BookViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.img_book_cover)
        ImageView mBookCoverImg;
        @BindView(R.id.tv_book_name)
        TextView mBookNameTv;
        @BindView(R.id.chk_book_delete)
        CheckBox mDeleteChk;
        @BindView(R.id.tv_book_badge)
        TextView mBookBadgeTv;
        public BookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
