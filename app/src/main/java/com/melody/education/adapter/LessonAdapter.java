package com.melody.education.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.melody.education.R;
import com.melody.education.fragment.ConversationFragment;
import com.melody.education.model.Conversation;
import com.melody.education.model.Lesson;
import com.melody.education.ui.ConversationActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class LessonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ADS = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_HEADER = 2;
    private Context mContext;
    public static List<Lesson> lessonArrayList = new ArrayList<>();

    private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, des;
        public ImageView thumbnail;
        private RelativeLayout body;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_title);
            des = (TextView) view.findViewById(R.id.tv_des);
            thumbnail = (ImageView) view.findViewById(R.id.iv_bg_lesson);
            body = (RelativeLayout) view.findViewById(R.id.body);
        }
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

    public LessonAdapter(Context mContext, List<Lesson> lessonArrayList) {
        this.mContext = mContext;
        this.lessonArrayList = lessonArrayList;
    }

    public void setModel(List<Lesson> lessonArrayList) {
        this.lessonArrayList = lessonArrayList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_header, parent, false);
            return new HeaderViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_lesson, parent, false);

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
            Lesson item = lessonArrayList.get(position);
            myViewHolder.title.setText(item.Anh);
            myViewHolder.des.setText(item.Detail);

            Picasso.with(mContext)
                    .load(R.drawable.album4)
                    .placeholder(R.drawable.album1)
                    .into(myViewHolder.thumbnail);

            //Click item
            ((MyViewHolder) holder).body.setOnClickListener(v -> startLearningActivity(position));

        } else if (holder instanceof AdsHolder) {
            //cast holder to VHHeader and set data for header.
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
        return lessonArrayList.size();
    }

    private void startLearningActivity(int selectedIndex) {
        Intent intent = new Intent(mContext, ConversationActivity.class);
        intent.putExtra(ConversationFragment.EXTRA_INDEX, selectedIndex);
        mContext.startActivity(intent);
    }
}
