package com.melody.education.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melody.education.R;
import com.melody.education.model.Conversation;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;


public class ConversationFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ADS = 0;
    private static final int TYPE_ITEM = 1;
    private Context mContext;
    public static List<Conversation> conversationList = new ArrayList<>();
    private List<Boolean> isExpandList = new ArrayList<>();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvEnglish, tvRomaji, tvJapanese;
        public ExpandableLayout expandableLayout;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_conversation_name);
            tvEnglish = (TextView) view.findViewById(R.id.tv_english);
            tvRomaji = (TextView) view.findViewById(R.id.tv_romaji);
            tvJapanese = (TextView) view.findViewById(R.id.tv_japanese);
            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandable_layout);
        }
    }

    class AdsHolder extends RecyclerView.ViewHolder {
        public AdsHolder(View itemView) {
            super(itemView);
        }
    }


    public ConversationFragmentAdapter(Context mContext, List<Conversation> conversationList) {
        this.mContext = mContext;
        this.conversationList = conversationList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_fragment_conversation, parent, false);

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
            if (conversationList.size() > 0) {
                Conversation item = conversationList.get(position);
                myViewHolder.tvName.setText(item.Ten + ":");
                myViewHolder.tvEnglish.setText(item.Anh);
                myViewHolder.tvJapanese.setText(item.Nhat);
                myViewHolder.tvRomaji.setText(item.Romaji);

                myViewHolder.tvEnglish.setOnClickListener(v -> {
                    if (myViewHolder.expandableLayout.isExpanded())
                        myViewHolder.expandableLayout.collapse();
                    else
                        myViewHolder.expandableLayout.expand();

                });

                if (position % 2 != 0) {
                    myViewHolder.tvName.setTextColor(Color.MAGENTA);
                }

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
        isExpandList.clear();
        for (Conversation c : conversationList) {
            isExpandList.add(false);
        }
        return conversationList.size();
    }
}
