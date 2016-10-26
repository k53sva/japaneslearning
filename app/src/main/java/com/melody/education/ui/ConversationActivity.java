package com.melody.education.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import com.melody.education.R;
import com.melody.education.adapter.ViewPagerAdapter;
import com.melody.education.fragment.ConversationFragment;
import com.melody.education.fragment.NotesFragment;
import com.melody.education.fragment.VocabularyFragment;
import com.viewpagerindicator.PageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by K53SV on 8/29/2016.
 */
public class ConversationActivity extends BaseActivity {
    private ViewPagerAdapter adapter;
    private int selectedIndex;
    @BindView(R.id.viewpager_learning)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        retrieveExtras();
        setUpView();
        setTab();
    }

    private void retrieveExtras() {
        Bundle extras = getIntent().getExtras();
        selectedIndex = extras.getInt(ConversationFragment.EXTRA_INDEX, 0);
    }

    private void setUpView() {
        setSupportActionBar(toolbar);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(ConversationFragment.newInstance(selectedIndex), getString(R.string.fragment_lesson_title));
        adapter.addFragment(VocabularyFragment.newInstance(selectedIndex), getString(R.string.fragment_vocabulary_title));
        adapter.addFragment(NotesFragment.newInstance(selectedIndex), getString(R.string.fragment_note_title));
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);

        getSupportActionBar().setTitle(adapter.getPageTitle(0));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        PageIndicator titleIndicator = (PageIndicator) findViewById(R.id.indicator);
        titleIndicator.setViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(3);
    }

    private void setTab() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int position) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setTitle(adapter.getPageTitle(position));
            }

        });

    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
