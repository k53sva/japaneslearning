package com.melody.education.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.melody.education.R;
import com.melody.education.fragment.ConversationFragment;
import com.melody.education.model.SyllabariesModel;
import com.melody.education.ui.ConversationActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SyllabariesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ADS = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_HEADER = 2;
    private Context mContext;
    public List<SyllabariesModel> syllabariesModels = new ArrayList<>();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivContent;
        private RelativeLayout item;

        public MyViewHolder(View view) {
            super(view);
            ivContent = (ImageView) view.findViewById(R.id.iv_japanese);
            item = (RelativeLayout) view.findViewById(R.id.item_content);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }


    class AdsHolder extends RecyclerView.ViewHolder {
        public AdsHolder(View itemView) {
            super(itemView);
        }
    }


    public SyllabariesAdapter(Context mContext, List<SyllabariesModel> syllabariesModels) {
        this.mContext = mContext;
        this.syllabariesModels = syllabariesModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_header, parent, false);
            return new HeaderViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_syllabaries, parent, false);

            return new MyViewHolder(itemView);
        } else {
            //inflate your layout and pass it to view holder
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false);
            return new AdsHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            SyllabariesModel item = syllabariesModels.get(position);
            myViewHolder.ivContent.setImageResource(item.image);
            if (item.charLatinh.length() > 0)
                myViewHolder.item.setOnClickListener(v -> playAudio(item.charLatinh));
        } else if (holder instanceof AdsHolder) {
            //cast holder to VHHeader and set data for header.
        } else if (holder instanceof HeaderViewHolder) {

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (syllabariesModels.get(position).charLatinh.length() > 0) {
            return TYPE_ITEM;

        } else {
            return TYPE_ADS;
        }

    }

    @Override
    public int getItemCount() {
        return syllabariesModels.size();
    }

    private void playAudio(String name) {
        try {
            int resID = mContext.getResources().getIdentifier(name, "raw", mContext.getPackageName());
            MediaPlayer mediaPlayer = MediaPlayer.create(mContext, resID);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
