package com.melody.education.ui.kanji;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melody.education.App;
import com.melody.education.R;
import com.melody.education.adapter.ViewPagerAdapter;
import com.melody.education.model.KanjiContent;
import com.melody.education.model.KanjiGroup;
import com.melody.education.ui.BaseFragment;
import com.melody.education.ui.MainActivity;
import com.melody.education.ui.kanji.KanjiContentFragment;
import com.melody.education.ui.kanji.KanjiLevelFragment;
import com.melody.education.ui.syllabaries.HiraganaFragment;
import com.melody.education.ui.syllabaries.KatakanaFragment;
import com.melody.education.utils.DataCache;
import com.melody.education.utils.DataHelper;
import com.melody.education.utils.Utils;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by K53SV on 8/29/2016.
 */
public class KanjiFragment extends BaseFragment {
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    TabLayout tabLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_syllabaries, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        getData();
        return v;
    }

    private void getData() {
        DataHelper helper = new DataHelper(getActivity());
        helper.getData(DataHelper.DATABASE_KANJI, DataHelper.TABLE_KANJI_GROUP, KanjiGroup[].class)
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .toSortedList((p1, p2) -> p1.ChungID.compareToIgnoreCase(p2.ChungID))
                .map(this::fillListKanji)
                .flatMap(Observable::from)
                .groupBy(m -> m.id / 20)
                //.publish(groups -> groups.map(g -> new Pair<>(g.getKey(), g.takeUntil(groups))))
                .doOnNext(group -> {
                    KanjiLevelFragment fragment = new KanjiLevelFragment();
                    adapter.addFragment(fragment, String.format("Level %s", group.getKey() + 1));
                    group.toList().subscribe(fragment::setModel);
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    viewPager.setAdapter(adapter);
                    tabLayout.setupWithViewPager(viewPager);
                }, Throwable::printStackTrace);
    }

    private List<KanjiGroup> fillListKanji(List<KanjiGroup> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).id = i;
        }
        return list;
    }
}
