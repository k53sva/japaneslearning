package com.melody.education.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.melody.education.App;
import com.melody.education.R;
import com.melody.education.model.AnswerModel;
import com.melody.education.model.AnswerShortQuiz1;
import com.melody.education.model.QuizModel;
import com.melody.education.model.ShortQuiz;
import com.melody.education.utils.DataHelper;

import java.util.HashMap;
import java.util.List;

import rx.Observable;

/**
 * Created by K53SV on 11/14/2016.
 */

public class LessonQuizItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 1;
    private Context mContext;
    public AnswerShortQuiz1[] answerShortQuiz1List;
    private QuizModel quizModel = new QuizModel();
    private boolean isKanji;

    public static final HashMap<Integer, Boolean> tempKanji = new HashMap<>();
    public static final HashMap<Integer, Boolean> tempRomaji = new HashMap<>();

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvStt;
        public RecyclerView recyclerView;
        public ImageView ivCheck;

        public MyViewHolder(View view) {
            super(view);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            ivCheck = (ImageView) view.findViewById(R.id.iv_check);
            tvStt = (TextView) view.findViewById(R.id.stt);
        }
    }

    private class SpinHolder extends RecyclerView.ViewHolder {
        public Spinner spinner;

        public SpinHolder(View view) {
            super(view);
            spinner = (Spinner) view.findViewById(R.id.spinner);
        }
    }

    public LessonQuizItemAdapter(Context mContext, List<ShortQuiz> items, boolean isKanji) {
        tempKanji.size();
        tempRomaji.clear();
        this.mContext = mContext;
        this.quizModel.items = items;
        this.isKanji = isKanji;
        Stream.of(items).forEach(x -> {
            if (x.AnswerID != null)
                this.quizModel.isCheck.put(x.id, false);
            else
                this.quizModel.isCheck.put(-1, false);
        });
        App.getDataHelper().getData(DataHelper.DATABASE_LESSON, DataHelper.TABLE_ANSWER_QUIZ, AnswerShortQuiz1[].class)
                .subscribe(m -> answerShortQuiz1List = m, Throwable::printStackTrace);
    }

    public void setQuizModel(List<ShortQuiz> items) {
        this.quizModel.items = items;
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
        ShortQuiz item = quizModel.items.get(position);
        if (holder instanceof LessonQuizItemAdapter.MyViewHolder) {
            LessonQuizItemAdapter.MyViewHolder v = (LessonQuizItemAdapter.MyViewHolder) holder;
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            v.recyclerView.setLayoutManager(mLayoutManager);
            if (quizModel.stt.get(position) == -1) {
                if (item.KanjiSentence1.contains(item.idCon + ".")) {
                    v.tvStt.setText(item.idCon + ".");
                    item.KanjiSentence1 = item.KanjiSentence1.substring(2);
                    item.RomajiSentence1 = item.RomajiSentence1.substring(2);
                    quizModel.stt.set(position, Integer.valueOf(item.idCon));
                    Log.e("TAG", "pos=" + position + ";id=" + item.idCon);
                } else {
                    v.tvStt.setText("");
                }
            } else
                v.tvStt.setText(quizModel.stt.get(position) + ".");
            Observable.just(item)
                    .map(this::mapLanguage)
                    .flatMap(Observable::from)
                    .map(String::trim)
                    .flatMap(m -> Observable.just(m.trim(), "_"))
                    .toList()
                    .subscribe(m -> {
                        m.remove(m.size() - 1);
                        if (isKanji) {
                            AnswerModel model = new AnswerModel();
                            model.values = m;
                            QuizKanjiAdapter adapter = new QuizKanjiAdapter(mContext, m, mapPair(item.AnswerID), item.AnswerID, item.id);
                            v.recyclerView.setAdapter(adapter);
                        } else {
                            QuizRomajiAdapter adapter =
                                    new QuizRomajiAdapter(mContext, m, mapPair(item.AnswerID), item.AnswerID, item.id);
                            v.recyclerView.setAdapter(adapter);
                        }
                    }, Throwable::printStackTrace);
            if (quizModel.status == QuizModel.RESET || !item.RomajiSentence1.contains("_"))
                v.ivCheck.setVisibility(View.GONE);
            else {
                v.ivCheck.setVisibility(View.VISIBLE);
                if (quizModel.isCheck.get(item.id)) {
                    v.ivCheck.setImageResource(R.drawable.ic_true);
                } else
                    v.ivCheck.setImageResource(R.drawable.ic_flase);
            }
        }
    }

    private String[] mapLanguage(ShortQuiz quiz) {
        if (isKanji)
            return quiz.KanjiSentence1.split("_");
        else
            return quiz.RomajiSentence1.split("_");
    }

    public void checkAnswer() {
        quizModel.status = QuizModel.CHECKED;
        if (isKanji)
            quizModel.isCheck = tempKanji;
        else
            quizModel.isCheck = tempRomaji;
        notifyDataSetChanged();
        for (int i = 0; i < quizModel.stt.size(); i++) {
            Log.e("TAG", "pos=" + i + ";id=" + quizModel.stt.get(i));
        }
    }

    public void resetAnswer() {
        quizModel.status = QuizModel.RESET;
        notifyDataSetChanged();
    }

    private AnswerShortQuiz1 mapPair(String id) {
        for (AnswerShortQuiz1 a : answerShortQuiz1List) {
            if (String.valueOf(a.id).equals(id))
                return a;
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return quizModel.items.size();
    }
}
