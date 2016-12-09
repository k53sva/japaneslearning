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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.melody.education.R;
import com.melody.education.ui.conversation.ConversationFragment;
import com.melody.education.model.Conversation;
import com.melody.education.net.FetchData;
import com.melody.education.service.MediaService;
import com.melody.education.ui.conversation.ConversationActivity;
import com.melody.education.utils.DataCache;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;


public class ConversationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ADS = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_HEADER = 2;
    private Context mContext;
    public static List<Conversation> conversationList = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView favorite;
        public ImageView thumbnail;
        private RelativeLayout body;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            favorite = (ImageView) view.findViewById(R.id.iv_favorite);
            body = (RelativeLayout) view.findViewById(R.id.item_content);
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

    public ConversationAdapter(Context mContext, List<Conversation> conversationList) {
        this.mContext = mContext;
        this.conversationList = conversationList;
    }

    public void setModel(List<Conversation> list) {
        this.conversationList = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_header, parent, false);
            return new HeaderViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
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
            Conversation item = conversationList.get(position);
            myViewHolder.title.setText(item.Title);
            myViewHolder.count.setText(item.Description);

            Glide.with(mContext)
                    .load(FetchData.ROOT_URL + item.Picture)
                    .placeholder(R.drawable.logo_app)
                    .into(myViewHolder.thumbnail);
            Integer fa = Hawk.get(item.id + "");
            if (fa != null && fa == item.id) {
                myViewHolder.favorite.setImageResource(R.drawable.ic_like);
                myViewHolder.favorite.setOnClickListener(v -> {
                    Hawk.delete(item.id + "");
                    ((ImageView) v).setImageResource(R.drawable.ic_favorite);

                    Toast.makeText(mContext, "You have to remove '" + item.Title + "' to favorite", Toast.LENGTH_LONG).show();

                });
            } else {
                myViewHolder.favorite.setImageResource(R.drawable.ic_favorite);
                myViewHolder.favorite.setOnClickListener(v -> {
                    ((ImageView) v).setImageResource(R.drawable.ic_like);
                    Hawk.put(item.id + "", item.id);
                    Toast.makeText(mContext, "You have to add '" + item.Title + "' to favorite", Toast.LENGTH_LONG).show();
                });
            }


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
        return conversationList.size();
    }

    private void startLearningActivity(int selectedIndex) {
        Intent isv = new Intent(mContext, MediaService.class);
        Intent intent = new Intent(mContext, ConversationActivity.class);
        intent.putExtra(ConversationFragment.EXTRA_INDEX, selectedIndex);
        DataCache.getInstance().push(conversationList.get(selectedIndex));
        mContext.startActivity(intent);
    }
}
