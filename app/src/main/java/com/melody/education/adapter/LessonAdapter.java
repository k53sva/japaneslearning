package com.melody.education.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.melody.education.R;
import com.melody.education.model.LessonTitle;
import com.melody.education.ui.lesson.LessonActivity;
import com.melody.education.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class LessonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ADS = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_HEADER = 2;
    private Context mContext;
    SharedPreferences preferences;

    public static List<LessonTitle> lessonArrayList = new ArrayList<>();

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

    public LessonAdapter(Context mContext, List<LessonTitle> lessonArrayList) {
        this.mContext = mContext;
        this.lessonArrayList = lessonArrayList;
        preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public void setModel(List<LessonTitle> lessonArrayList) {
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
            LessonTitle item = lessonArrayList.get(position);
            myViewHolder.title.setText(item.Title);
            myViewHolder.des.setText(item.Detail);
            Picasso.with(mContext)
                    .load(item.Picture)
                    .placeholder(R.drawable.album1)
                    .into(myViewHolder.thumbnail);

            //Click item
            ((MyViewHolder) holder).body.setOnClickListener(v -> startLearningActivity(item, position));

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

    private void startLearningActivity(LessonTitle item, int position) {
        int i = preferences.getInt(Utils.PRF_LESSON_FINAL, 0);
        if (position <= i)
            LessonActivity.launchActivity(mContext, item.ChungID, item.Title, item.Picture, position);
        else
            Toast.makeText(mContext, "You must complete the Lesson Quiz " + (i + 1), Toast.LENGTH_LONG).show();
    }

}
