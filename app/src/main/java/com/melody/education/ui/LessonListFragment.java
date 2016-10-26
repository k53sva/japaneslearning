package com.melody.education.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melody.education.R;
import com.melody.education.adapter.LessonAdapter;
import com.melody.education.model.Lesson;
import com.melody.education.utils.GridSpacingItemDecoration;
import com.melody.education.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by K53SV on 8/29/2016.
 */
public class LessonListFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private LessonAdapter adapter;
    private List<Lesson> lessonList = new ArrayList<>();
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_main, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, Utils.dpToPx(getActivity(), 0), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        lessonList = new ArrayList<>();
        adapter = new LessonAdapter(getActivity(), lessonList);
        recyclerView.setAdapter(adapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this::refreshItems);
        AppBarLayout appBarLayout = ((MainActivity) getActivity()).appBarLayout;
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        lp.height = Utils.dpToPx(getActivity(), 200);
        appBarLayout.setLayoutParams(lp);

        getData();
        return v;
    }

    void refreshItems() {
        new Handler().postDelayed(this::onItemsLoadComplete, 5000);
    }

    void onItemsLoadComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void getData() {

        for (int i = 0; i < 10; i++) {
            lessonList.add(new Lesson("ALIGATO", "ahihi do ngoc"));
        }

        adapter.notifyDataSetChanged();
    }

}
