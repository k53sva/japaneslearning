package com.melody.education.ui.lesson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melody.education.App;
import com.melody.education.adapter.LessonKeySentencesAdapter;
import com.melody.education.databinding.FragmentKanjiContentBinding;
import com.melody.education.databinding.FragmentNotesBinding;
import com.melody.education.model.KanjiContent;
import com.melody.education.model.KeySentences;
import com.melody.education.model.Note;
import com.melody.education.model.Notes;
import com.melody.education.ui.BaseFragment;
import com.melody.education.ui.kanji.viewmodel.KanjiContentModel;
import com.melody.education.utils.DataHelper;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Created by K53SV on 11/3/2016.
 */

public class NotesFragment extends BaseFragment {
    public static final String TAG = NotesFragment.class.getSimpleName();
    public static final String EXTRA_INDEX = "EXTRA_INDEX";
    private FragmentNotesBinding binding;
    private Notes notes;
    private String ChungID;

    public static NotesFragment newInstance(String index) {
        NotesFragment fragment = new NotesFragment();
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
        binding = FragmentNotesBinding.inflate(inflater, container, false);
        notes = new Notes();
        binding.setNotes(notes);
        retrieveExtras();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        App.getDataHelper().getData(DataHelper.DATABASE_LESSON, DataHelper.TABLE_NOTES, Notes[].class)
                .flatMap(Observable::from)
                .filter(m -> m.ChungID.equals(ChungID))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(m -> notes.setView(m.Detail));
    }
}
