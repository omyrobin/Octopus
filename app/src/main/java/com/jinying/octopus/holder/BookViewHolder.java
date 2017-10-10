package com.jinying.octopus.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinying.octopus.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by omyrobin on 2017/8/23.
 */

public class BookViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.rl_book_parent)
    public RelativeLayout mBookParentRl;
    @BindView(R.id.img_book_cover)
    public ImageView mBookCoverImg;
    @BindView(R.id.tv_book_name)
    public TextView mBookNameTv;
    @BindView(R.id.tv_book_actor)
    public TextView mBookActorTv;
    @BindView(R.id.tv_book_brief)
    public TextView mBookBriefTv;
    @BindView(R.id.tv_book_readcount)
    public TextView mBookReadCountTv;
    public BookViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
