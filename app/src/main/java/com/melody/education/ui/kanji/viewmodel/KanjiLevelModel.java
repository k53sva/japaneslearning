package com.melody.education.ui.kanji.viewmodel;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.melody.education.BR;
import com.melody.education.R;
import com.melody.education.binding.RecyclerBindingAdapter;
import com.melody.education.binding.fields.ObservableBoolean;
import com.melody.education.binding.fields.RecyclerConfiguration;
import com.melody.education.model.KanjiGroup;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by K53SV on 11/3/2016.
 */

public class KanjiLevelModel {
    public static final String TAG = KanjiLevelModel.class.getSimpleName();
    public final RecyclerConfiguration recyclerConfiguration = new RecyclerConfiguration();
    private Context context;
    private RecyclerBindingAdapter<ItemKanjiLevel>
            adapter = new RecyclerBindingAdapter<>(R.layout.item_kanji_level, BR.itemKanji, new ArrayList<>());

    public KanjiLevelModel(Context context) {
        this.context = context;
        initRecycler();
    }

    public void setItems(List<KanjiGroup> list) {
        Observable.from(list)
                .map(m -> new ItemKanjiLevel(m.Title))
                .toList()
                .map(ArrayList::new)
                .subscribe(m -> adapter.setItems(m));
    }

    private void initRecycler() {
        LinearLayoutManager layout = new LinearLayoutManager(context);
        recyclerConfiguration.setLayoutManager(layout);
        recyclerConfiguration.setItemAnimator(new DefaultItemAnimator());
        recyclerConfiguration.setItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = dpToPx(context, 10);
            }
        });

        recyclerConfiguration.setAdapter(adapter);
        adapter.setOnItemClickListener((position, item) -> {
        });
    }

    public static int dpToPx(Context c, int dp) {
        Resources r = c.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
