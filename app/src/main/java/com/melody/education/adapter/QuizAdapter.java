package com.melody.education.adapter;

import android.content.Context;
import android.databinding.tool.util.L;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.annimon.stream.Stream;
import com.melody.education.R;
import com.melody.education.model.Conversation;
import com.melody.education.model.QuizModel;
import com.melody.education.utils.DataCache;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_SPINNER = 0;
    private static final int TYPE_ITEM = 1;
    private Context mContext;
    private List<String> items = new ArrayList<>();

    private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;


        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_name);
        }
    }

    private class SpinHolder extends RecyclerView.ViewHolder {
        public Spinner spinner;

        public SpinHolder(View view) {
            super(view);
            spinner = (Spinner) view.findViewById(R.id.spinner);
        }
    }

    public QuizAdapter(Context mContext, List<String> items) {
        this.mContext = mContext;
        this.items = items;
    }

    public void setModel(List<String> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_textview, parent, false);

            return new MyViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_spinner, parent, false);

            return new SpinHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        String item = items.get(position);
        if (holder instanceof MyViewHolder) {
            MyViewHolder v = (MyViewHolder) holder;
            v.tvName.setText(item);
        }

        if (holder instanceof SpinHolder) {
            SpinHolder v = (SpinHolder) holder;
            String[] data = {"---", "one", "two", "three"};
            ArrayAdapter ap = new ArrayAdapter<>(mContext, R.layout.item_textview_spinner, data);
            v.spinner.setAdapter(ap);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) != null)
            return TYPE_ITEM;
        return TYPE_SPINNER;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
