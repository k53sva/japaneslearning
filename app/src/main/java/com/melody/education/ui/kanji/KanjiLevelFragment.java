package com.melody.education.ui.kanji;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melody.education.databinding.FragmentKanjiLevelBinding;
import com.melody.education.model.KanjiGroup;
import com.melody.education.ui.BaseFragment;
import com.melody.education.ui.kanji.viewmodel.KanjiLevelModel;

import java.util.List;

/**
 * Created by K53SV on 11/3/2016.
 */

public class KanjiLevelFragment extends BaseFragment {
    private FragmentKanjiLevelBinding binding;
    private KanjiLevelModel viewModel;
    private List<KanjiGroup> listGroup;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentKanjiLevelBinding.inflate(inflater, container, false);
        viewModel = new KanjiLevelModel(getActivity());
        binding.setKanjiLevel(viewModel);
        viewModel.setItems(listGroup);

        return binding.getRoot();
    }

    public void setModel(List<KanjiGroup> list) {
        this.listGroup = list;
    }
}
