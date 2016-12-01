package com.melody.education.ui.lesson.viewmodel;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.jakewharton.rxbinding.view.RxView;
import com.melody.education.App;
import com.melody.education.R;
import com.melody.education.adapter.LessonQuizItemAdapter;
import com.melody.education.model.ShortQuiz;
import com.melody.education.ui.BaseFragment;
import com.melody.education.ui.lesson.LessonActivity;
import com.melody.education.utils.DataHelper;
import com.melody.education.utils.Utils;

import java.util.ArrayList;
import java.util.Map;

import rx.Observable;

/**
 * Created by K53SV on 11/14/2016.
 */

public class KanjiQuizFragment extends BaseFragment {
    RecyclerView recyclerView;
    LessonQuizItemAdapter adapter;
    Button btnCheck, btnReset, btnAnswer;
    int total;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public static final String EXTRA_INDEX = "EXTRA_INDEX";
    private String ChungID;


    public static KanjiQuizFragment newInstance(String index) {
        KanjiQuizFragment fragment = new KanjiQuizFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void retrieveExtras() {
        if (getArguments() != null) {
            ChungID = getArguments().getString(EXTRA_INDEX);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_quiz, container, false);
        retrieveExtras();
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        btnCheck = (Button) v.findViewById(R.id.btn_check);
        btnReset = (Button) v.findViewById(R.id.btn_reset);
        btnAnswer = (Button) v.findViewById(R.id.btn_answer);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new LessonQuizItemAdapter(getActivity(), new ArrayList<>(), true);
        recyclerView.setAdapter(adapter);
        getData();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        RxView.clicks(btnCheck).subscribe(v -> adapter.checkAnswer());
        RxView.clicks(btnAnswer).subscribe(v -> {
            adapter.checkAnswer();
            saveData();
        });
        RxView.clicks(btnReset)
                .doOnNext(v -> App.getApplication().getCheckKanji().clear())
                .subscribe(v -> adapter.resetAnswer());

    }

    private void saveData() {
        int i = preferences.getInt(Utils.PRF_LESSON_FINAL, 0);
        if (LessonActivity.lessonId == i) {
            total = LessonQuizItemAdapter.tempRomaji.size() + LessonQuizItemAdapter.tempKanji.size();
            int x = +Stream.of(LessonQuizItemAdapter.tempKanji).map(Map.Entry::getValue).filter(m -> m).collect(Collectors.toList()).size()
                    + Stream.of(LessonQuizItemAdapter.tempRomaji).map(Map.Entry::getValue).filter(m -> m).collect(Collectors.toList()).size();
            if ((Utils.checkFinal(x, total))) {
                editor.putInt(Utils.PRF_LESSON_FINAL, LessonActivity.lessonId);
            }
            editor.apply();
        }
    }

    private void getData() {
        App.getDataHelper().getData(DataHelper.DATABASE_LESSON, DataHelper.TABLE_SHORT_QUIZ, ShortQuiz[].class)
                .flatMap(Observable::from)
                .filter(m -> m.ChungID.equals(ChungID))
                .toList()
                .subscribe(m -> {
                    adapter.setModel(m);
                }, Throwable::printStackTrace);
    }
}
