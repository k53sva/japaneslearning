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
import com.melody.education.model.Lesson;
import com.melody.education.ui.ConversationActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ConversationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ADS = 0;
    private static final int TYPE_ITEM = 1;
    private Context mContext;
    public static List<Lesson> conversationList = new ArrayList<>();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView overflow;
        public ImageView thumbnail;
        private RelativeLayout body;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            body = (RelativeLayout) view.findViewById(R.id.item_content);
        }
    }

    class AdsHolder extends RecyclerView.ViewHolder {
        public AdsHolder(View itemView) {
            super(itemView);
        }
    }


    public ConversationAdapter(Context mContext, List<Lesson> lessonList) {
        this.mContext = mContext;
        this.conversationList = lessonList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_conversation, parent, false);

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
            Lesson item = conversationList.get(position);
            myViewHolder.title.setText(item.LessonName);
            myViewHolder.count.setText(item.Anh + "\n" + item.Nhat + "\n" + item.Romaji + "\n" + item.Detail);
            if (position % 2 == 0)
                Picasso.with(mContext)
                        .load("http://www.japaneselearning.somee.com/image/beginner_lesson_1.jpg")
                        .placeholder(R.drawable.album1)
                        .into(myViewHolder.thumbnail);
            else

                Picasso.with(mContext)
                        .load("http://japaneselearning.comli.com/lesson2.gif")
                        .placeholder(R.drawable.album1)
                        .into(myViewHolder.thumbnail);
            //Click item
            ((MyViewHolder) holder).body.setOnClickListener(v -> startLearningActivity(position));

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
        return conversationList.size();
    }

    private void startLearningActivity(int selectedIndex) {
        Intent intent = new Intent(mContext, ConversationActivity.class);
        intent.putExtra(ConversationFragment.EXTRA_INDEX, selectedIndex);
        mContext.startActivity(intent);
    }
}
