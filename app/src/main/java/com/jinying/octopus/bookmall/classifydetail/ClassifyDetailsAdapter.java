package com.jinying.octopus.bookmall.classifydetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinying.octopus.R;
import com.jinying.octopus.bean.TagBookBean;
import com.jinying.octopus.bookdetails.BookDetailsActivity;
import com.jinying.octopus.holder.BookViewHolder;
import com.jinying.octopus.image.ImageLoader;
import com.jinying.octopus.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by omyrobin on 2017/8/25.
 */

public class ClassifyDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TagBookBean.ListBean> list;

    private List<TagBookBean.ListBean> datas;

    private Context context;

    public static final int TYPE_EMPTY = 0;

    public static final int TYPE_ITEM = 1;

    public ClassifyDetailsAdapter(Context context, List<TagBookBean.ListBean> list) {
        this.context = context;
        this.list = list;
        datas = new ArrayList<>();
        datas.addAll(list);
        notifyDataSetChanged();
    }

    public void addNotifyList(List<TagBookBean.ListBean> list) {
        if(list.isEmpty()){
            ToastUtil.showShort(context, "已经加载全部数据");
            return;
        }
        if(!datas.containsAll(list)){
            datas.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void setNotifyList(List<TagBookBean.ListBean> list) {
        datas = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return !datas.isEmpty() ? datas.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(getItemCount() == 1 && datas.isEmpty()){
            return TYPE_EMPTY;
        }
        return TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) != TYPE_EMPTY){
            final TagBookBean.ListBean entity = datas.get(position);
            ImageLoader.loadNovelCover(context, entity.getCover(), ((TagScreenViewHolder)holder).mBookCoverImg);
            ((TagScreenViewHolder)holder).mBookNameTv.setText(entity.getName());
            ((TagScreenViewHolder)holder).mBookActorTv.setText(entity.getAuthor());
            ((BookViewHolder)holder).mBookReadCountTv.setText(entity.getPvAll() +"次阅读");
            ((TagScreenViewHolder)holder).mBookBriefTv.setText(entity.getIntroduce().trim().replaceAll("\\s*", ""));
            ((TagScreenViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BookDetailsActivity.newInstance(context, entity.getId()+"");
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        if(type == TYPE_EMPTY){
            return new EmptyItemViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_item_empty_tag_layout, parent, false));
        }else{
            return new TagScreenViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_item_book, parent, false));
        }
    }

    static class TagScreenViewHolder extends BookViewHolder{

        public TagScreenViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class EmptyItemViewHolder extends RecyclerView.ViewHolder{

        public EmptyItemViewHolder(View itemView) {
            super(itemView);
        }
    }

}
