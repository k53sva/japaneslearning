package com.melody.education.ui.topic;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.annimon.stream.Stream;
import com.melody.education.R;
import com.melody.education.databinding.ActivityTopicsBinding;
import com.melody.education.model.Topic;
import com.melody.education.net.FetchData;
import com.melody.education.ui.BaseActivity;
import com.melody.education.utils.DataHelper;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Created by K53SV on 10/26/2016.
 */

public class TopicDetailActivity extends BaseActivity {
    public static final String KEY_EXTRA = "KEY_EXTRA";
    private ActivityTopicsBinding binding;
    private TopicDetailViewModel viewModel;
    private String ChungId;

    public static void launchActivity(Context context, String ChungId) {
        Intent intent = new Intent(context, TopicDetailActivity.class);
        intent.putExtra(KEY_EXTRA, ChungId);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null)
            ChungId = getIntent().getExtras().getString(KEY_EXTRA);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_topics);
        viewModel = new TopicDetailViewModel(this);
        binding.setTopicModel(viewModel);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getData();
    }

    private void getData() {
        DataHelper dataHelper = new DataHelper(this);
        dataHelper.getData(DataHelper.DATABASE_TOPICS, DataHelper.TABLE_TOPIC, Topic[].class)
                .flatMap(Observable::from)
                .filter(m -> m.ChungID.equals(ChungId))
                .distinctUntilChanged()
                .map(this::fillData)
                .toList()
                .map(ArrayList::new)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(m -> viewModel.setItems(m));
    }

    private Topic fillData(Topic t) {
        t.ImageUrl = String.format("%s%s", FetchData.TOPICS_URL, t.ImageUrl);
        Observable.just(t.AudioUrl)
                .filter(m -> m != null)
                .filter(m -> m.length() > 0)
                .doOnNext(m -> t.isSound = true)
                .subscribe(m -> t.AudioUrl = String.format("%s%s", FetchData.TOPICS_URL, m));

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
}
