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
import com.melody.education.fragment.LessonFragment;
import com.melody.education.model.Album;
import com.melody.education.ui.LearningActivity;
import com.squareup.picasso.Picasso;

import java.util.List;



public class LessonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ADS = 0;
    private static final int TYPE_ITEM = 1;
    private Context mContext;
    private List<Album> albumList;


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


    public LessonAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_lesson, parent, false);

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
            Album album = albumList.get(position);
            myViewHolder.title.setText(album.getName());
            myViewHolder.count.setText(album.getDescription());
            Picasso.with(mContext)
                    .load("http://www.japaneselearning.somee.com/image/beginner_lesson_1.jpg")
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
        return albumList.size();
    }

    private void startLearningActivity(int selectedIndex) {
        Intent intent = new Intent(mContext, LearningActivity.class);
        intent.putExtra(LessonFragment.EXTRA_INDEX, selectedIndex);
        mContext.startActivity(intent);
    }
}
