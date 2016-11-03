package com.melody.education.ui.kanji;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
    public static final String EXTRA_KEY_LEVEL = "LEVEL";

    private FragmentKanjiLevelBinding binding;
    private KanjiLevelModel viewModel;
    private List<KanjiGroup> listGroup;

    public static KanjiLevelFragment newInstance(int level) {
        KanjiLevelFragment fragment = new KanjiLevelFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_KEY_LEVEL, level);
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
