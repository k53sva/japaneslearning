package com.melody.education.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.melody.education.R;
import com.melody.education.model.Note;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by K53SV on 8/31/2016.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.VocabularyHolder> {
    List<Note> noteList = new ArrayList<>();
    Activity activity;

    public NoteAdapter(Activity activity, List<Note> noteList) {
        this.activity = activity;
        this.noteList = noteList;
    }

    @Override
    public VocabularyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);

        return new VocabularyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VocabularyHolder holder, int position) {
        Note item = noteList.get(position);
        holder.tvFrom.setText(item.Form);
        holder.tvExplain.setText(item.Explain);
        holder.tvStt.setText((position + 1) + ". ");

        holder.content.setOnClickListener(v -> {
            if (holder.expandableLayout.isExpanded())
                holder.expandableLayout.collapse();
            else
                holder.expandableLayout.expand();
        });

    }

    public void setModel(List<Note> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class VocabularyHolder extends RecyclerView.ViewHolder {
        TextView tvFrom;
        TextView tvExplain;
        TextView tvStt;
        LinearLayout content;
        public ExpandableLayout expandableLayout;

        public VocabularyHolder(View itemView) {
            super(itemView);
            tvFrom = (TextView) itemView.findViewById(R.id.tv_note_from);
            tvExplain = (TextView) itemView.findViewById(R.id.tv_note_explain);
            tvStt = (TextView) itemView.findViewById(R.id.tv_stt);
            content = (LinearLayout) itemView.findViewById(R.id.item_content);
            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandable_layout);
        }
    }
}
