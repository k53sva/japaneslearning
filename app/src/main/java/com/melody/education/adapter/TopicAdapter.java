package com.melody.education.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.melody.education.R;
import com.melody.education.model.TopicTitle;
import com.melody.education.net.FetchData;
import com.melody.education.ui.topic.TopicDetailActivity;
import com.melody.education.utils.DataHelper;

import java.util.ArrayList;
import java.util.List;



public class TopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ADS = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_HEADER = 2;
    private Activity mContext;
    private DataHelper dataHelper;
    private List<TopicTitle> topics = new ArrayList<>();

    private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView des;
        public ImageView thumbnail;
        private RelativeLayout body;
        private LinearLayout ln;


        public MyViewHolder(View view) {
            super(view);
            des = (TextView) view.findViewById(R.id.tv_des);
            thumbnail = (ImageView) view.findViewById(R.id.iv_bg_lesson);
            body = (RelativeLayout) view.findViewById(R.id.body);
            ln = (LinearLayout) view.findViewById(R.id.content);
        }
    }

    public void setModel(List<TopicTitle> list) {
        this.topics = list;
        notifyDataSetChanged();
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class AdsHolder extends RecyclerView.ViewHolder {
        public AdsHolder(View itemView) {
            super(itemView);
        }
    }

    public TopicAdapter(Activity mContext, List<TopicTitle> topics) {
        this.mContext = mContext;
        this.topics = topics;
        dataHelper = new DataHelper(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_header, parent, false);
            return new HeaderViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_topic, parent, false);

            return new MyViewHolder(itemView);
        } else {
            //inflate your layout and pass it to view holder
            return new AdsHolder(null);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            TopicTitle item = topics.get(position);
            String detail = item.TitleDetail;
            if (detail != null && detail.length() > 0) {
                myViewHolder.des.setText(detail);
                myViewHolder.ln.setVisibility(View.VISIBLE);
            } else {
                myViewHolder.ln.setVisibility(View.GONE);
            }
            Glide.with(mContext).load(FetchData.ROOT_URL + item.TopicImage)
                    .placeholder(R.drawable.logo_app)
                    .into(myViewHolder.thumbnail);

            ((MyViewHolder) holder).body.setOnClickListener(v ->
                    TopicDetailActivity.launchActivity(mContext, topics.get(position).ChungID));

        } else if (holder instanceof AdsHolder) {
        } else if (holder instanceof HeaderViewHolder) {

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 99999) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }

    }

    @Override
    public int getItemCount() {
        return topics.size();
    }
}
