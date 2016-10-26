package com.melody.education.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.melody.education.R;
import com.melody.education.adapter.ConversationAdapter;
import com.melody.education.model.Topic;
import com.melody.education.model.Vocabulary;
import com.melody.education.utils.DataHelper;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by K53SV on 9/13/2016.
 */
public class TopicActivity extends BaseActivity {
    public static final String KEY_EXTRA = "KEY_EXTRA";
    private RecyclerView recyclerView;
    Toolbar mToolbar;
    private String ChungId;
    DataHelper dataHelper;

    public static void lauchActivity(Context context, String ChungId) {
        Intent intent = new Intent(context, TopicActivity.class);
        intent.putExtra(KEY_EXTRA, ChungId);
        context.startActivity(intent);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null)
            ChungId = getIntent().getExtras().getString(KEY_EXTRA);
        dataHelper = new DataHelper(this);

        setContentView(R.layout.activity_topic);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

    }

    private void getData() throws JSONException {
        Gson gson = new Gson();
        Observable.just(dataHelper.convertDatabaseToJson(DataHelper.DATABASE_TOPICS, DataHelper.TABLE_TOPIC))
                .subscribeOn(Schedulers.io())
                .map(m -> gson.fromJson(m.toString(), Topic[].class))
                .flatMap(Observable::from)
                .filter(m -> m.ChungID.equals(ChungId))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(m -> {
                });
    }
}
