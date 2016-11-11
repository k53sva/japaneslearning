package com.melody.education.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.melody.education.R;
import com.melody.education.model.KeySentences;
import com.melody.education.model.LessonVocabulary;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by K53SV on 8/31/2016.
 */
public class LessonVocabularyAdapter extends RecyclerView.Adapter<LessonVocabularyAdapter.VocabularyHolder> {
    List<LessonVocabulary> models = new ArrayList<>();
    Activity activity;

    public LessonVocabularyAdapter(Activity activity, List<LessonVocabulary> models) {
        this.activity = activity;
        this.models = models;
    }

    @Override
    public VocabularyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lesson_vocabulary, parent, false);

        return new VocabularyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VocabularyHolder holder, int position) {
        LessonVocabulary item = models.get(position);
        holder.tvKanji.setText(item.Kanji);
        holder.tvROmaji.setText(item.Romaji);
        holder.tvTranslate.setText(item.English);
        holder.tvStt.setText((position + 1) + ". ");

        holder.content.setOnClickListener(v -> {
            if (holder.expandableLayout.isExpanded())
                holder.expandableLayout.collapse();
            else
                holder.expandableLayout.expand();
        });

    }

    public void setModel(List<LessonVocabulary> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class VocabularyHolder extends RecyclerView.ViewHolder {
        TextView tvKanji;
        TextView tvROmaji;
        TextView tvTranslate;
        TextView tvStt;
        LinearLayout content;
        public ExpandableLayout expandableLayout;

        public VocabularyHolder(View itemView) {
            super(itemView);
            tvKanji = (TextView) itemView.findViewById(R.id.tv_kanji);
            tvROmaji = (TextView) itemView.findViewById(R.id.tv_romaji);
            tvTranslate = (TextView) itemView.findViewById(R.id.tv_translate);
            tvStt = (TextView) itemView.findViewById(R.id.tv_stt);
            content = (LinearLayout) itemView.findViewById(R.id.item_content);
            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandable_layout);
        }
    }
}
