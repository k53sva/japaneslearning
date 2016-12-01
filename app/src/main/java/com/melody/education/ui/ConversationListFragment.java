package com.melody.education.ui;

import android.content.Intent;
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
import android.widget.ProgressBar;

import com.melody.education.App;
import com.melody.education.R;
import com.melody.education.adapter.ConversationAdapter;
import com.melody.education.model.Conversation;
import com.melody.education.model.Version;
import com.melody.education.net.FetchData;
import com.melody.education.utils.DataHelper;
import com.melody.education.utils.GridSpacingItemDecoration;
import com.melody.education.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by K53SV on 8/29/2016.
 */
public class ConversationListFragment extends BaseFragment {
    private static final String TAG = ConversationListFragment.class.getSimpleName();
    private ConversationAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_main, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) v.findViewById(R.id.loading);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, Utils.dpToPx(getActivity(), 0), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new ConversationAdapter(getActivity(), new ArrayList<>());
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
        new FetchData(getActivity()).getDataConversation()
                .doOnNext(m -> mSwipeRefreshLayout.setRefreshing(false))
                .filter(m -> m)
                .subscribe(m -> getData());
    }

    private void getData() {
        progressBar.setVisibility(View.VISIBLE);
        App.getDataHelper().getData(DataHelper.DATABASE_CONVERSATION, DataHelper.TABLE_CONVERSATION, Conversation[].class)
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .filter(m -> m.Picture != null)
                .filter(m -> m.Picture.length() > 0)
                .map(this::fillAudio)
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(m -> progressBar.setVisibility(View.GONE))
                .doOnNext(this::getDataConversation)
                .subscribe(m -> adapter.setModel(m));
    }

    private void getDataConversation(List<Conversation> l) {
        if (l.size() == 0)
            new FetchData(getActivity()).getDataConversation()
                    .subscribe(m -> {
                        if (m) {
                            getData();
                        } else
                            showAlertAction(getActivity(), id -> startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS))
                                    , "No connection. Please check connect internet");
                    });
    }

    private Conversation fillAudio(Conversation c) {
        c.Audio = String.format("%s%s", FetchData.ROOT_URL, c.Audio);
        return c;
    }
}
