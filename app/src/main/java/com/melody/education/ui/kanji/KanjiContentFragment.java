package com.melody.education.ui.kanji;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melody.education.databinding.FragmentKanjiContentBinding;
import com.melody.education.model.KanjiContent;
import com.melody.education.ui.BaseFragment;
import com.melody.education.ui.kanji.viewmodel.KanjiContentModel;


/**
 * Created by K53SV on 11/3/2016.
 */

public class KanjiContentFragment extends BaseFragment {
    public static final String TAG = KanjiContentFragment.class.getSimpleName();
    private FragmentKanjiContentBinding binding;
    private KanjiContentModel viewModel;
    private KanjiContent content;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentKanjiContentBinding.inflate(inflater, container, false);
        viewModel = new KanjiContentModel(getActivity(), content);
        binding.setKanjiContent(viewModel);

        return binding.getRoot();
    }

    public void setModel(KanjiContent content) {
        this.content = content;
    }
}
