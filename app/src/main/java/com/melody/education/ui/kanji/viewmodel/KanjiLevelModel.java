package com.melody.education.ui.kanji.viewmodel;

import android.content.Context;
import android.content.Intent;
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
import com.melody.education.binding.fields.RecyclerConfiguration;
import com.melody.education.model.KanjiGroup;
import com.melody.education.net.FetchData;
import com.melody.education.ui.kanji.KanjiContentActivity;
import com.melody.education.utils.DataCache;

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
    private List<KanjiGroup> list;
    private RecyclerBindingAdapter<KanjiGroup>
            adapter = new RecyclerBindingAdapter<>(R.layout.item_kanji_level, BR.itemKanji, new ArrayList<>());

    public KanjiLevelModel(Context context) {
        this.context = context;
        initRecycler();
    }

    public void setItems(List<KanjiGroup> list) {
        this.list = list;
        Observable.from(list)
                .map(this::fillImage)
                .toList()
                .map(ArrayList::new)
                .subscribe(m -> adapter.setItems(m));
    }

    private KanjiGroup fillImage(KanjiGroup g) {
        g.TitleImage = FetchData.ROOT_URL + "kanji/" + g.TitleImage;
        g.Title = g.Title.toUpperCase();
        return g;
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
            DataCache.getInstance().push(list.get(position));
            context.startActivity(new Intent(context, KanjiContentActivity.class));
        });
    }

    public static int dpToPx(Context c, int dp) {
        Resources r = c.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
