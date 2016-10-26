package com.melody.education.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.melody.education.R;
import com.melody.education.fragment.ConversationFragment;
import com.melody.education.model.Topic;
import com.melody.education.model.TopicTitle;
import com.melody.education.net.FetchData;
import com.melody.education.ui.TopicActivity;
import com.melody.education.utils.DataHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class TopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ADS = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_HEADER = 2;
    private Activity mContext;
    private Gson gson = new Gson();
    public static List<Topic> topics = new ArrayList<>();

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

    public void setModel(List<Topic> list) {
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

    public TopicAdapter(Activity mContext, List<Topic> topics) {
        this.mContext = mContext;
        this.topics = topics;
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
            Topic item = topics.get(position);
            myViewHolder.title.setText(item.LessonName.toUpperCase());
            myViewHolder.des.setText(item.Detail);
            Observable.just(new DataHelper(mContext).convertDatabaseToJson(DataHelper.DATABASE_TOPICS, DataHelper.TABLE_TOPIC_TITLE))
                    .subscribeOn(Schedulers.io())
                    .map(m -> gson.fromJson(m.toString(), TopicTitle[].class))
                    .filter(m -> m.length > 0)
                    .map(m -> m[0])
                    .map(m -> FetchData.ROOT_URL + m.TopicImage)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(m ->
                            Picasso.with(mContext).load(m)
                                    .placeholder(R.drawable.album1)
                                    .into(myViewHolder.thumbnail)
                    );
            ((MyViewHolder) holder).body.setOnClickListener(v -> startLearningActivity(position));

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

    private void startLearningActivity(int selectedIndex) {
        Intent intent = new Intent(mContext, TopicActivity.class);
        intent.putExtra(ConversationFragment.EXTRA_INDEX, selectedIndex);
        mContext.startActivity(intent);
    }
}
