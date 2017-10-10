package com.jinying.octopus.search.result;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinying.octopus.R;
import com.jinying.octopus.bean.StoryVoBean;
import com.jinying.octopus.bookdetails.BookDetailsActivity;
import com.jinying.octopus.holder.BookViewHolder;
import com.jinying.octopus.image.ImageLoader;

import java.util.ArrayList;

/**
 * Created by omyrobin on 2017/8/23.
 */

public class SearchResultAdapter extends RecyclerView.Adapter{

    private Context context;

    private ArrayList<StoryVoBean> list;

    public SearchResultAdapter(Context context, ArrayList<StoryVoBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0){
            View itemView = LayoutInflater.from(context).inflate(R.layout.adapter_item_search_result_header,parent, false);
            return new TitleViewHolder(itemView);
        }else{
            View itemView = LayoutInflater.from(context).inflate(R.layout.adapter_item_book, parent, false);
            return new BookViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int ViewType = getItemViewType(position);
        if(ViewType != 0){
            final StoryVoBean bean = list.get(position-1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((BookViewHolder)holder).mBookParentRl.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_item_click_background));
            }else{
                ((BookViewHolder)holder).mBookParentRl.setBackgroundResource(android.R.color.white);
            }
            ((BookViewHolder)holder).mBookNameTv.setText(bean.getName());
            ((BookViewHolder)holder).mBookActorTv.setText(bean.getAuthorName());
            ((BookViewHolder)holder).mBookBriefTv.setText(bean.getAppIntroduce());
            ((BookViewHolder)holder).mBookReadCountTv.setText(bean.getPv() +"次阅读");
            ImageLoader.loadNovelCover(context,bean.getCover(),((BookViewHolder)holder).mBookCoverImg);
            ((BookViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BookDetailsActivity.newInstance(context, bean.getId());
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return  0;
        }
        return 1;
    }

    @Override
    public int getItemCount() {
        return list == null ? 1 : list.size() + 1;
    }

    public void addBookList(ArrayList<StoryVoBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    static class TitleViewHolder extends RecyclerView.ViewHolder{

        public TitleViewHolder(View itemView) {
            super(itemView);
        }
    }
}
