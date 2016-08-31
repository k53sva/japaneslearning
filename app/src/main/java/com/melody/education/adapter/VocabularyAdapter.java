package com.melody.education.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melody.education.R;
import com.melody.education.model.Vocabulary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by K53SV on 8/31/2016.
 */
public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.VocabularyHolder> {
    List<Vocabulary> vocabularyList = new ArrayList<>();
    Activity activity;

    public VocabularyAdapter(Activity activity, List<Vocabulary> vocabularyList) {
        this.activity = activity;
        this.vocabularyList = vocabularyList;
    }

    @Override
    public VocabularyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vocabulary, parent, false);

        return new VocabularyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VocabularyHolder holder, int position) {
        Vocabulary item = vocabularyList.get(position);
        holder.tvEnglish.setText(item.Anh);
        holder.tvROmaji.setText(item.NhatRomaji);
        holder.tvDetail.setText(item.Detail);

    }

    @Override
    public int getItemCount() {
        return vocabularyList.size();
    }

    public class VocabularyHolder extends RecyclerView.ViewHolder {
        TextView tvEnglish;
        TextView tvROmaji;
        TextView tvDetail;

        public VocabularyHolder(View itemView) {
            super(itemView);
            tvEnglish = (TextView) itemView.findViewById(R.id.tv_vocabulary_english);
            tvROmaji = (TextView) itemView.findViewById(R.id.tv_vocabulary_romaji);
            tvDetail = (TextView) itemView.findViewById(R.id.tv_vocabulary_detail);
        }
    }
}
