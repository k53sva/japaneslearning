package com.melody.education.ui.kanji;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.MenuItem;

import com.melody.education.R;
import com.melody.education.model.KanjiGroup;
import com.melody.education.utils.DataHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by K53SV on 11/2/2016.
 */

public class KanjiActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanji);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        getData();
    }

    private void getData() {
        DataHelper helper = new DataHelper(this);
        helper.getData(DataHelper.DATABASE_KANJI, DataHelper.TABLE_KANJI_GROUP, KanjiGroup[].class)
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .toSortedList((p1, p2) -> p1.ChungID.compareToIgnoreCase(p2.ChungID))
                .map(this::fillListKanji)
                .flatMap(Observable::from)
                .groupBy(m -> m.id / 10)
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

    private List<KanjiGroup> fillListKanji(List<KanjiGroup> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).id = i;
        }
        return list;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
