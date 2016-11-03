package com.melody.education.ui.kanji;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.melody.education.R;
import com.melody.education.model.KanjiContent;
import com.melody.education.model.KanjiGroup;
import com.melody.education.utils.DataCache;
import com.melody.education.utils.DataHelper;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by K53SV on 11/2/2016.
 */

public class KanjiContentActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private PageIndicator titleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanji_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        titleIndicator = (PageIndicator) findViewById(R.id.indicator);

        getData();
    }

    private void getData() {
        KanjiGroup group = DataCache.getInstance().pop(KanjiGroup.class);
        DataHelper helper = new DataHelper(this);
        helper.getData(DataHelper.DATABASE_KANJI, DataHelper.TABLE_KANJI_CONTENT, KanjiContent[].class)
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .filter(m -> m.ChungID.equals(group.ChungID))
                .toSortedList((p1, p2) -> p1.KanjiNumber.compareToIgnoreCase(p2.KanjiNumber))
                .flatMap(Observable::from)
                .doOnNext(m -> {
                    KanjiContentFragment fragment = new KanjiContentFragment();
                    adapter.addFragment(fragment, "");
                    fragment.setModel(m);
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    viewPager.setAdapter(adapter);
                    titleIndicator.setViewPager(viewPager);
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
