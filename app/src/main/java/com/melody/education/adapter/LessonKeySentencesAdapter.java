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
import com.melody.education.model.Vocabulary;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by K53SV on 8/31/2016.
 */
public class LessonKeySentencesAdapter extends RecyclerView.Adapter<LessonKeySentencesAdapter.VocabularyHolder> {
    List<KeySentences> models = new ArrayList<>();
    Activity activity;

    public LessonKeySentencesAdapter(Activity activity, List<KeySentences> models) {
        this.activity = activity;
        this.models = models;
    }

    @Override
    public VocabularyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_key_sentences, parent, false);

        return new VocabularyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VocabularyHolder holder, int position) {
        KeySentences item = models.get(position);
        holder.tvKanji.setText(item.Kanji);
        holder.tvROmaji.setText(item.Romaji);
        holder.tvTranslate.setText(item.Translate);
        holder.tvNote.setText(item.Note);
        holder.tvStt.setText((position + 1) + ". ");

        holder.content.setOnClickListener(v -> {
            if (holder.expandableLayout.isExpanded())
                holder.expandableLayout.collapse();
            else
                holder.expandableLayout.expand();
        });

    }

    public void setModel(List<KeySentences> models) {
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
        TextView tvNote;
        TextView tvStt;
        LinearLayout content;
        public ExpandableLayout expandableLayout;

        public VocabularyHolder(View itemView) {
            super(itemView);
            tvKanji = (TextView) itemView.findViewById(R.id.tv_kanji);
            tvROmaji = (TextView) itemView.findViewById(R.id.tv_romaji);
            tvTranslate = (TextView) itemView.findViewById(R.id.tv_translate);
            tvNote = (TextView) itemView.findViewById(R.id.tv_note);
            tvStt = (TextView) itemView.findViewById(R.id.tv_stt);
            content = (LinearLayout) itemView.findViewById(R.id.item_content);
            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandable_layout);
        }
    }
}
