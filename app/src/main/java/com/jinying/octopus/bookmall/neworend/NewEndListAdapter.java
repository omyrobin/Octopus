package com.jinying.octopus.bookmall.neworend;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;

import com.jinying.octopus.adapter.BaseBookAdapter;
import com.jinying.octopus.bean.BookListBean;
import com.jinying.octopus.bookdetails.BookDetailsActivity;
import com.jinying.octopus.holder.BookViewHolder;
import com.jinying.octopus.image.ImageLoader;

import java.util.List;

/**
 * Created by omyrobin on 2017/8/25.
 */

public class NewEndListAdapter extends BaseBookAdapter<BookListBean.ListBean> {

    public NewEndListAdapter(Context context, List<BookListBean.ListBean> list) {
        super(context, list);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        final BookListBean.ListBean bean = list.get(position);
        holder.mBookNameTv.setText(bean.getName());
        holder.mBookActorTv.setText(bean.getAuthor());
        holder.mBookBriefTv.setText(bean.getIntroduce());
        holder.mBookReadCountTv.setText(bean.getPv() +"人次阅读");
        ImageLoader.loadNovelCover(context,bean.getCover(),holder.mBookCoverImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookDetailsActivity.newInstance(context, bean.getId());
            }
        });
    }
}
