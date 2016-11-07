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

import com.annimon.stream.Stream;
import com.melody.education.BR;
import com.melody.education.R;
import com.melody.education.binding.RecyclerBindingAdapter;
import com.melody.education.binding.fields.ObservableBoolean;
import com.melody.education.binding.fields.ObservableString;
import com.melody.education.binding.fields.RecyclerConfiguration;
import com.melody.education.model.Examples;
import com.melody.education.model.KanjiContent;
import com.melody.education.model.Reference;
import com.melody.education.model.WordKunReading;
import com.melody.education.model.WordOnReading;
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
    public static final String FUNCTION_NAME = "kanji/";
    public static final String TAG = KanjiContentModel.class.getSimpleName();
    public final ObservableString name = new ObservableString();
    public final ObservableString image = new ObservableString();
    public final ObservableString onR = new ObservableString();
    public final ObservableString kunR = new ObservableString();
    public final ObservableString means = new ObservableString();
    public final ObservableString wOn = new ObservableString();
    public final ObservableString wKun = new ObservableString();
    public final ObservableBoolean isExample = new ObservableBoolean();
    private DataHelper helper;
    public KanjiContent content;
    private Context context;
    public final RecyclerConfiguration recyclerConfiguration = new RecyclerConfiguration();
    private RecyclerBindingAdapter<Reference>
            adapter = new RecyclerBindingAdapter<>(R.layout.item_kanji_reference, BR.reference, new ArrayList<>());

    public final RecyclerConfiguration configurationWordOn = new RecyclerConfiguration();
    private RecyclerBindingAdapter<WordOnReading>
            adapterWordOn = new RecyclerBindingAdapter<>(R.layout.item_kanji_word_on, BR.wordOn, new ArrayList<>());

    public final RecyclerConfiguration configurationWordKun = new RecyclerConfiguration();
    private RecyclerBindingAdapter<WordKunReading>
            adapterWordKun = new RecyclerBindingAdapter<>(R.layout.item_kanji_word_kun, BR.wordKun, new ArrayList<>());

    public final RecyclerConfiguration configurationExample = new RecyclerConfiguration();
    private RecyclerBindingAdapter<Examples>
            adapterExample = new RecyclerBindingAdapter<>(R.layout.item_kanji_example, BR.example, new ArrayList<>());


    public KanjiContentModel(Context context, KanjiContent content) {
        this.context = context;
        helper = new DataHelper((Activity) context);
        String image = FetchData.ROOT_URL + FUNCTION_NAME + content.KanjiIMG;
        onR.set(content.OnReading);
        kunR.set(content.KunReading);
        means.set(content.Meaning);
        name.set(content.KanjiName);
        isExample.set(false);
        this.image.set(image);
        initRecycler();
        initRecyclerExample();
        initRecyclerWordOn();
        initRecyclerWordKun();
        setReference(content);
        setWord(content);
    }

    public void setReference(KanjiContent content) {
        helper.getData(DataHelper.DATABASE_KANJI, DataHelper.TABLE_REFRENCE, Reference[].class)
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .filter(m -> m.KanjiNumber.equals(content.KanjiNumber))
                .toList()
                .map(ArrayList::new)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(m -> adapter.setItems(m));
    }

    private WordKunReading fillDataKun(WordKunReading kun) {
        kun.Sound = FetchData.ROOT_URL + FUNCTION_NAME + kun.Sound;
        return kun;
    }

    private WordOnReading fillDataOn(WordOnReading on) {
        on.Sound = FetchData.ROOT_URL + FUNCTION_NAME + on.Sound;
        return on;
    }

    private Examples fillExample(Examples ex) {
        ex.Sound = FetchData.ROOT_URL + FUNCTION_NAME + ex.Sound;
        return ex;
    }

    private void setWord(KanjiContent content) {
        helper.getData(DataHelper.DATABASE_KANJI, DataHelper.TABLE_WORK_ON_READING, WordOnReading[].class)
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .filter(m -> m.KanjiNumber.equals(content.KanjiNumber))
                .map(this::fillDataOn)
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(m -> wOn.set(Stream.of(m)
                        .map(n -> n.KanjiLook)
                        .filter(n -> n != null)
                        .filter(n -> n.length() > 0)
                        .reduce("", (x, y) -> {
                            if (x.length() > 0)
                                return String.format("%s,%s", x, y);
                            else
                                return y;
                        })))
                .map(ArrayList::new)
                .subscribe(m -> adapterWordOn.setItems(m));

        helper.getData(DataHelper.DATABASE_KANJI, DataHelper.TABLE_WORK_KUN_READING, WordKunReading[].class)
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .filter(m -> m.KanjiNumber.equals(content.KanjiNumber))
                .map(this::fillDataKun)
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(m -> wKun.set(Stream.of(m)
                        .map(n -> n.KanjiLook)
                        .filter(n -> n != null)
                        .filter(n -> n.length() > 0)
                        .reduce("", (x, y) -> {
                            if (x.length() > 0)
                                return String.format("%s,%s", x, y);
                            else
                                return y;
                        })))
                .map(ArrayList::new)
                .subscribe(m -> adapterWordKun.setItems(m));

        helper.getData(DataHelper.DATABASE_KANJI, DataHelper.TABLE_EXAMPLES, Examples[].class)
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .filter(m -> m.KanjiNumber.equals(content.KanjiNumber))
                .map(this::fillExample)
                .toList()
                .filter(m -> m.size() > 0)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(m -> isExample.set(true))
                .map(ArrayList::new)
                .subscribe(m -> adapterExample.setItems(m));


    }


    private void initRecycler() {
        LinearLayoutManager layout = new LinearLayoutManager(context);
        recyclerConfiguration.setLayoutManager(layout);
        recyclerConfiguration.setItemAnimator(new DefaultItemAnimator());
        recyclerConfiguration.setItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = dpToPx(context, 2);
            }
        });

        recyclerConfiguration.setAdapter(adapter);
        adapter.setOnItemClickListener((position, item) -> {
        });
    }

    private void initRecyclerExample() {
        LinearLayoutManager layout = new LinearLayoutManager(context);
        configurationExample.setLayoutManager(layout);
        configurationExample.setItemAnimator(new DefaultItemAnimator());
        configurationExample.setItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = dpToPx(context, 2);
            }
        });

        configurationExample.setAdapter(adapterExample);
        adapterExample.setOnItemClickListener((position, item) -> {
        });
    }

    private void initRecyclerWordOn() {
        LinearLayoutManager layout = new LinearLayoutManager(context);
        configurationWordOn.setLayoutManager(layout);
        configurationWordOn.setItemAnimator(new DefaultItemAnimator());
        configurationWordOn.setItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = dpToPx(context, 2);
            }
        });

        configurationWordOn.setAdapter(adapterWordOn);
        adapterWordOn.setOnItemClickListener((position, item) -> {
        });
    }

    private void initRecyclerWordKun() {
        LinearLayoutManager layout = new LinearLayoutManager(context);
        configurationWordKun.setLayoutManager(layout);
        configurationWordKun.setItemAnimator(new DefaultItemAnimator());
        configurationWordKun.setItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = dpToPx(context, 2);
            }
        });

        configurationWordKun.setAdapter(adapterWordKun);
        adapterWordKun.setOnItemClickListener((position, item) -> {
        });
    }

    public static int dpToPx(Context c, int dp) {
        Resources r = c.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
