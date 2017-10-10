package com.jinying.octopus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinying.octopus.R;
import com.jinying.octopus.holder.BookViewHolder;

import java.util.List;

/**
 * Created by omyrobin on 2017/8/25.
 */

public abstract class BaseBookAdapter<T> extends RecyclerView.Adapter<BookViewHolder> {

    protected Context context;

    protected List<T> list;

    public BaseBookAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.adapter_item_book, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void addBookList(List<T> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}
