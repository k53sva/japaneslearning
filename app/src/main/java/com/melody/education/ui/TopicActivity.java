package com.melody.education.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.melody.education.R;
import com.melody.education.adapter.TopicDetailAdapter;
import com.melody.education.model.Topic;
import com.melody.education.net.FetchData;
import com.melody.education.utils.DataHelper;
import com.melody.education.utils.GridSpacingItemDecoration;
import com.melody.education.utils.Utils;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by K53SV on 9/13/2016.
 */
public class TopicActivity extends BaseActivity {
    public static final String KEY_EXTRA = "KEY_EXTRA";
    private Toolbar mToolbar;
    private String ChungId;
    private TopicDetailAdapter adapter;

    public static void launchActivity(Context context, String ChungId) {
        Intent intent = new Intent(context, TopicActivity.class);
        intent.putExtra(KEY_EXTRA, ChungId);
        context.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null)
            ChungId = getIntent().getExtras().getString(KEY_EXTRA);
        setContentView(R.layout.activity_topic);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, Utils.dpToPx(this, 0), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new TopicDetailAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);
        getData();
    }

    private void getData() {
        DataHelper dataHelper = new DataHelper(this);
        dataHelper.getData(DataHelper.DATABASE_TOPICS, DataHelper.TABLE_TOPIC, Topic[].class)
                .flatMap(Observable::from)
                .filter(m -> m.ChungID.equals(ChungId))
                .map(this::fillData)
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(m -> adapter.setModel(m));
    }

    private Topic fillData(Topic t) {
        t.ImageUrl = String.format("%s%s", FetchData.TOPICS_URL, t.ImageUrl);
        t.AudioUrl = String.format("%s%s", FetchData.TOPICS_URL, t.AudioUrl);
        return t;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.stopAudio();
    }
}
