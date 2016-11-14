package com.melody.education.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.melody.education.R;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by K53SV on 11/14/2016.
 */

public class LessonQuizItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_SPINNER = 0;
    private static final int TYPE_ITEM = 1;
    private Context mContext;
    private List<String> items = new ArrayList<>();

    private class MyViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView recyclerView;

        public MyViewHolder(View view) {
            super(view);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        }
    }

    private class SpinHolder extends RecyclerView.ViewHolder {
        public Spinner spinner;

        public SpinHolder(View view) {
            super(view);
            spinner = (Spinner) view.findViewById(R.id.spinner);
        }
    }

    public LessonQuizItemAdapter(Context mContext, List<String> items) {
        this.mContext = mContext;
        this.items = items;
    }

    public void setModel(List<String> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_quiz, null);
            return new LessonQuizItemAdapter.MyViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_spinner, parent, false);

            return new LessonQuizItemAdapter.SpinHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        String item = items.get(position);
        if (holder instanceof LessonQuizItemAdapter.MyViewHolder) {
            LessonQuizItemAdapter.MyViewHolder v = (LessonQuizItemAdapter.MyViewHolder) holder;
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            v.recyclerView.setLayoutManager(mLayoutManager);
            Observable.just(item)
                    .map(m -> m.split("_"))
                    .flatMap(Observable::from)
                    .map(String::trim)
                    .doOnNext(m -> Log.e("S_", m))
                    .flatMap(m -> Observable.just(m.trim(), null))
                    .toList()
                    .doOnNext(m -> Log.e("TAG", m.size() + ""))
                    .subscribe(m -> {
                        m.remove(m.size() - 1);
                        QuizAdapter adapter = new QuizAdapter(mContext, m);
                        v.recyclerView.setAdapter(adapter);
                    }, Throwable::printStackTrace);
        }

        if (holder instanceof LessonQuizItemAdapter.SpinHolder) {
        }
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
