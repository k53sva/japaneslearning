package com.melody.education.ui.lesson;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melody.education.R;
import com.melody.education.adapter.LessonKeySentencesAdapter;
import com.melody.education.adapter.LessonVocabularyAdapter;
import com.melody.education.model.KeySentences;
import com.melody.education.model.LessonVocabulary;
import com.melody.education.utils.DataHelper;
import com.melody.education.utils.GridSpacingItemDecoration;
import com.melody.education.utils.Utils;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by K53SV on 8/29/2016.
 */
public class LessonVocabularyFragment extends Fragment {
    public static final String EXTRA_INDEX = "EXTRA_INDEX";
    private String ChungID;
    private LessonVocabularyAdapter adapter;

    public static LessonVocabularyFragment newInstance(String index) {
        LessonVocabularyFragment fragment = new LessonVocabularyFragment();
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vocabulary, container, false);
        initView(v);
        retrieveExtras();
        getData();
        return v;
    }

    private void initView(View v) {
        adapter = new LessonVocabularyAdapter(getActivity(), new ArrayList<>());
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_vocabulary);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(10, Utils.dpToPx(getActivity(), 1), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        DataHelper helper = new DataHelper(getActivity());
        helper.getData(DataHelper.DATABASE_LESSON, DataHelper.TABLE_VOCABULARY, LessonVocabulary[].class)
                .flatMap(Observable::from)
                .filter(m -> m.ChungID.equals(ChungID))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(m -> adapter.setModel(m));
    }
}
