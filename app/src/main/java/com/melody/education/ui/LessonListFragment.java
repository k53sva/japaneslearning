package com.melody.education.ui;

import android.os.Bundle;
import android.os.Handler;
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
import com.melody.education.model.LessonTitle;
import com.melody.education.net.FetchData;
import com.melody.education.utils.DataHelper;
import com.melody.education.utils.GridSpacingItemDecoration;
import com.melody.education.utils.Utils;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by K53SV on 8/29/2016.
 */
public class LessonListFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private LessonAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_main, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, Utils.dpToPx(getActivity(), 0), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new LessonAdapter(getActivity(), new ArrayList<>());
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
        DataHelper dataHelper = new DataHelper(getActivity());
        dataHelper.getData(DataHelper.DATABASE_LESSON, DataHelper.TABLE_LESSON_TITLE, LessonTitle[].class)
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .map(this::fillData)
                .toList()
                .map(ArrayList::new)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(m -> adapter.setModel(m));
    }

    private LessonTitle fillData(LessonTitle title) {
        title.Picture = FetchData.ROOT_URL + "Lesson/" + title.Picture;
        return title;
    }
}
