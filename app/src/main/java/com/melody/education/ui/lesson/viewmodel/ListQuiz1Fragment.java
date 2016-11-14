package com.melody.education.ui.lesson.viewmodel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melody.education.App;
import com.melody.education.R;
import com.melody.education.adapter.LessonQuizItemAdapter;
import com.melody.education.model.ShortQuiz;
import com.melody.education.ui.BaseFragment;
import com.melody.education.utils.DataHelper;

import rx.Observable;

/**
 * Created by K53SV on 11/14/2016.
 */

public class ListQuiz1Fragment extends BaseFragment {
    RecyclerView recyclerView;
    LessonQuizItemAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_quiz, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        getData();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void getData() {
        App.getDataHelper().getData(DataHelper.DATABASE_LESSON, DataHelper.TABLE_SHORT_QUIZ, ShortQuiz[].class)
                .flatMap(Observable::from)
                .map(m -> m.KanjiSentence1)
                .toList()
                .doOnNext(m -> Log.e("TAG", m.size() + ""))
                .subscribe(m -> {
                    adapter = new LessonQuizItemAdapter(getActivity(), m);
                    recyclerView.setAdapter(adapter);
                }, Throwable::printStackTrace);
    }
}
