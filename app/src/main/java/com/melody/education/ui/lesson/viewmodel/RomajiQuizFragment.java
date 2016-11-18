package com.melody.education.ui.lesson.viewmodel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;
import com.melody.education.R;
import com.melody.education.adapter.LessonQuizItemAdapter;
import com.melody.education.model.ShortQuiz;
import com.melody.education.ui.BaseFragment;
import com.melody.education.utils.DataHelper;


import java.util.ArrayList;

import rx.Observable;

/**
 * Created by K53SV on 11/14/2016.
 */

public class RomajiQuizFragment extends BaseFragment {
    RecyclerView recyclerView;
    LessonQuizItemAdapter adapter;
    Button btnCheck;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_quiz, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        btnCheck = (Button) v.findViewById(R.id.btn_check);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new LessonQuizItemAdapter(getActivity(), new ArrayList<>(), false);
        recyclerView.setAdapter(adapter);
        getData();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        RxView.clicks(btnCheck).subscribe(v -> adapter.checkAnswer());
    }

    private void getData() {
        DataHelper helper = new DataHelper(getActivity());
        helper.getData(DataHelper.DATABASE_LESSON, DataHelper.TABLE_SHORT_QUIZ, ShortQuiz[].class)
                .flatMap(Observable::from)
                .toList()
                .subscribe(m -> {
                    adapter.setModel(m);
                }, Throwable::printStackTrace);
    }
}
