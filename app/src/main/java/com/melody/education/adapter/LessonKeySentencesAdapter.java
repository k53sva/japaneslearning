package com.melody.education.adapter;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.melody.education.R;
import com.melody.education.model.KeySentences;
import com.melody.education.net.FetchData;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.io.IOException;
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
        holder.tvRomaji.setText(item.Romaji);
        holder.tvTranslate.setText(item.Translate);
        holder.tvNote.setText(item.Note);
        holder.tvStt.setText((position + 1) + ". ");

        holder.tvKanji.setOnClickListener(v -> {
            if (holder.expandableLayout.isExpanded())
                holder.expandableLayout.collapse();
            else
                holder.expandableLayout.expand();
        });
        if (item.Audio.trim().equals(FetchData.ROOT_URL + "lesson/")) {
            holder.ivplay.setVisibility(View.GONE);

        } else {
            holder.ivplay.setVisibility(View.VISIBLE);
            holder.ivplay.setOnClickListener(v -> playAudio((ImageView) v, item.Audio));
        }
    }

    public void setModel(List<KeySentences> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    public void playAudio(ImageView view, String audio) {
        Glide.with(activity)
                .load(R.drawable.ic_loader)
                .into(view);
        view.setEnabled(false);
        try {
            MediaPlayer mp = new MediaPlayer();
            mp.setDataSource(audio);
            mp.setOnPreparedListener(m -> {
                mp.start();
                view.setImageResource(R.drawable.playlistcore_ic_pause_black);
            });
            mp.setOnCompletionListener(m -> {
                m.reset();
                view.setImageResource(R.drawable.playlistcore_ic_play_arrow_black);
                view.setEnabled(true);
            });
            mp.prepareAsync();

        } catch (IOException e) {
            view.setImageResource(R.drawable.playlistcore_ic_play_arrow_black);
            e.printStackTrace();
            view.setEnabled(true);
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class VocabularyHolder extends RecyclerView.ViewHolder {
        TextView tvKanji;
        TextView tvRomaji;
        TextView tvTranslate;
        TextView tvNote;
        TextView tvStt;
        ImageView ivplay;
        LinearLayout content;
        public ExpandableLayout expandableLayout;

        public VocabularyHolder(View itemView) {
            super(itemView);
            tvKanji = (TextView) itemView.findViewById(R.id.tv_kanji);
            tvRomaji = (TextView) itemView.findViewById(R.id.tv_romaji);
            tvTranslate = (TextView) itemView.findViewById(R.id.tv_translate);
            tvNote = (TextView) itemView.findViewById(R.id.tv_note);
            tvStt = (TextView) itemView.findViewById(R.id.tv_stt);
            content = (LinearLayout) itemView.findViewById(R.id.item_content);
            ivplay = (ImageView) itemView.findViewById(R.id.iv_play);
            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandable_layout);
        }
    }
}
