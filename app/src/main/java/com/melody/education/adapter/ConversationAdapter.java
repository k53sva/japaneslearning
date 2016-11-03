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
import com.melody.education.ui.fragment.ConversationFragment;
import com.melody.education.model.Conversation;
import com.melody.education.net.FetchData;
import com.melody.education.service.MediaService;
import com.melody.education.ui.ConversationActivity;
import com.melody.education.utils.DataCache;
import com.squareup.picasso.Picasso;

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

            Picasso.with(mContext)
                    .load(FetchData.ROOT_URL + item.Picture)
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
