package com.melody.education.ui.kanji.viewmodel;

import android.app.Activity;
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
import com.melody.education.binding.fields.ObservableString;
import com.melody.education.binding.fields.RecyclerConfiguration;
import com.melody.education.model.KanjiContent;
import com.melody.education.model.Reference;
import com.melody.education.net.FetchData;
import com.melody.education.utils.DataHelper;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by K53SV on 11/3/2016.
 */

public class KanjiContentModel {
    public static final String TAG = KanjiContentModel.class.getSimpleName();
    public final ObservableString name = new ObservableString();
    public final ObservableString image = new ObservableString();
    public final ObservableString onR = new ObservableString();
    public final ObservableString kunR = new ObservableString();
    public final ObservableString means = new ObservableString();
    public KanjiContent content;
    private Context context;
    public final RecyclerConfiguration recyclerConfiguration = new RecyclerConfiguration();
    private RecyclerBindingAdapter<Reference>
            adapter = new RecyclerBindingAdapter<>(R.layout.item_kanji_reference, BR.reference, new ArrayList<>());


    public KanjiContentModel(Context context, KanjiContent content) {
        this.context = context;
        String image = FetchData.ROOT_URL + "kanji/" + content.KanjiIMG;
        onR.set(content.OnReading);
        kunR.set(content.KunReading);
        means.set(content.Meaning);
        name.set(content.KanjiName);
        this.image.set(image);
        initRecycler();
        setReference(content);
    }

    public void setReference(KanjiContent content) {
        DataHelper helper = new DataHelper((Activity) context);
        helper.getData(DataHelper.DATABASE_KANJI, DataHelper.TABLE_REFRENCE, Reference[].class)
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .filter(m -> m.KanjiNumber.equals(content.KanjiNumber))
                .toList()
                .map(ArrayList::new)
                .observeOn(AndroidSchedulers.mainThread())
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
