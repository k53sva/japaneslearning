package com.melody.education.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.melody.education.R;
import com.melody.education.adapter.ConversationAdapter;
import com.melody.education.adapter.SyllabariesAdapter;
import com.melody.education.data.SyllabariesManager;
import com.melody.education.model.Conversation;
import com.melody.education.ui.BaseFragment;
import com.melody.education.utils.DataHelper;
import com.melody.education.utils.GridSpacingItemDecoration;
import com.melody.education.utils.Utils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by K53SV on 8/29/2016.
 */
public class HiraganaFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private SyllabariesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_char_janpanes, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 5);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(5, Utils.dpToPx(getActivity(), 5), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        adapter = new SyllabariesAdapter(getActivity(), SyllabariesManager.getHiragana());
        recyclerView.setAdapter(adapter);

        return v;
    }
}
