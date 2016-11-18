package com.melody.education.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.annimon.stream.Stream;
import com.github.davidmoten.rx.Transformers;
import com.github.davidmoten.rx.util.MapWithIndex;
import com.jakewharton.rxbinding.widget.RxAdapterView;
import com.melody.education.R;
import com.melody.education.model.AnswerShortQuiz1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;

public class QuizKanjiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_SPINNER = 0;
    private static final int TYPE_ITEM = 1;
    private Context mContext;
    private List<String> items = new ArrayList<>();
    private AnswerShortQuiz1 answer;
    private HashMap<Integer, String> check = new HashMap<>();
    private HashMap<Integer, Integer> tempSpinner = new HashMap<>();


    private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_name);
        }
    }

    private class SpinHolder extends RecyclerView.ViewHolder {
        public Spinner spinner;

        public SpinHolder(View view) {
            super(view);
            spinner = (Spinner) view.findViewById(R.id.spinner);
        }
    }

    public QuizKanjiAdapter(Context mContext, List<String> items, AnswerShortQuiz1 answer) {
        this.mContext = mContext;
        this.items = items;
        this.answer = answer;
    }

    public void setModel(List<String> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_textview, parent, false);

            return new MyViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_spinner, parent, false);

            return new SpinHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        String item = items.get(position);
        if (holder instanceof MyViewHolder) {
            MyViewHolder v = (MyViewHolder) holder;
            v.tvName.setText(item);
        }

        if (holder instanceof SpinHolder) {
            SpinHolder v = (SpinHolder) holder;
            Observable.from(answer.KanjiChoose.split(","))
                    .toList()
                    .subscribe(m -> {
                        m.add(0, "----");
                        ArrayAdapter<String> ap = new ArrayAdapter<>(mContext, R.layout.item_textview_spinner, m);
                        v.spinner.setAdapter(ap);
                        v.spinner.setSelection(getIndexSpinner(position));
                        RxAdapterView.itemSelections(v.spinner)
                                .doOnNext(i -> check.put(position, ap.getItem(i)))
                                .doOnNext(i -> tempSpinner.put(position, i))
                                .subscribe(i -> checkAnswer());

                    });
        }
    }

    private int getIndexSpinner(int position) {
        if (tempSpinner.get(position) != null)
            return tempSpinner.get(position);
        return 0;
    }

    private void checkAnswer() {
        String temp = Stream.of(check).reduce("", (x, y) -> x + ", " + y).replace(",", "");
        LessonQuizItemAdapter.tempKanji.put(answer.idCon, temp.trim().equals(answer.KanjiCorrect.replace(",", "").trim()));
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) != null)
            return TYPE_ITEM;
        return TYPE_SPINNER;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
