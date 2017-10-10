package com.jinying.octopus.bookmall.rankdetail;

import android.content.Context;
import android.view.View;

import com.jinying.octopus.adapter.BaseBookAdapter;
import com.jinying.octopus.bean.RankDetailBean;
import com.jinying.octopus.bookdetails.BookDetailsActivity;
import com.jinying.octopus.holder.BookViewHolder;
import com.jinying.octopus.image.ImageLoader;

import java.util.List;

/**
 * Created by omyrobin on 2017/8/25.
 */

public class RankDetailAdapter extends BaseBookAdapter<RankDetailBean.ListBean> {

    public RankDetailAdapter(Context context, List<RankDetailBean.ListBean> list) {
        super(context, list);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        final RankDetailBean.ListBean bean = list.get(position);
        holder.mBookNameTv.setText(bean.getName());
        holder.mBookActorTv.setText(bean.getAuthor());
        holder.mBookBriefTv.setText(bean.getIntroduce());
        holder.mBookReadCountTv.setText(bean.getPvAll() +"人次阅读");
        ImageLoader.loadNovelCover(context,bean.getCover(),holder.mBookCoverImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookDetailsActivity.newInstance(context, bean.getId() + "");
            }
        });
    }
}
