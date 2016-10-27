package com.melody.education.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.melody.education.R;
import com.melody.education.model.Topic;
import com.squareup.picasso.Picasso;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TopicDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ADS = 0;
    private static final int TYPE_ITEM = 1;
    private Context mContext;
    private List<Topic> topics = new ArrayList<>();
    MediaPlayer mp;

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAnh, tvNhat, tvRomaji, tvDetail;
        private ImageView ivDetail, ivPlay;
        public ExpandableLayout expandableLayout;

        public MyViewHolder(View view) {
            super(view);
            tvAnh = (TextView) view.findViewById(R.id.tv_anh);
            tvNhat = (TextView) view.findViewById(R.id.tv_nhat);
            tvRomaji = (TextView) view.findViewById(R.id.tv_romaji);
            tvDetail = (TextView) view.findViewById(R.id.tv_detail);
            ivDetail = (ImageView) view.findViewById(R.id.iv_detail);
            ivPlay = (ImageView) view.findViewById(R.id.iv_play);
            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandable_layout);
        }
    }

    private class AdsHolder extends RecyclerView.ViewHolder {
        public AdsHolder(View itemView) {
            super(itemView);
        }
    }

    public TopicDetailAdapter(Context mContext, List<Topic> topics) {
        this.mContext = mContext;
        this.topics = topics;
        mp = new MediaPlayer();
    }

    public void setModel(List<Topic> topics) {
        this.topics = topics;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_topic_detail, parent, false);

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
            if (topics.size() > 0) {
                Topic item = topics.get(position);
                myViewHolder.tvAnh.setText(item.Anh);
                myViewHolder.tvNhat.setText(item.Nhat);
                myViewHolder.tvRomaji.setText(item.Romaji);
                myViewHolder.tvDetail.setText(item.Detail);

                Picasso.with(mContext)
                        .load(item.ImageUrl)
                        .placeholder(R.drawable.album1)
                        .into(myViewHolder.ivDetail);

                myViewHolder.tvAnh.setOnClickListener(v -> {
                    if (myViewHolder.expandableLayout.isExpanded())
                        myViewHolder.expandableLayout.collapse();
                    else
                        myViewHolder.expandableLayout.expand();

                });


                myViewHolder.ivPlay.setOnClickListener(v -> {
                    if (mp.isPlaying()) {
                        mp.stop();
                        myViewHolder.ivPlay.setImageResource(R.drawable.playlistcore_ic_play_arrow_black);

                    } else {
                        playAudio(item.AudioUrl);
                        myViewHolder.ivPlay.setImageResource(R.drawable.playlistcore_ic_pause_black);
                    }
                });

            }
        } else if (holder instanceof AdsHolder) {
            //cast holder to VHHeader and set data for header.
        }
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    private void playAudio(String name) {
        try {
            mp.setDataSource(name);
            mp.setOnPreparedListener(m -> mp.start());
            mp.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopAudio() {
        if (mp != null)
            mp.stop();
    }
}
