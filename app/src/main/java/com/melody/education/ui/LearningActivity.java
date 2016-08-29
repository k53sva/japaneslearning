package com.melody.education.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.melody.education.R;
import com.melody.education.adapter.ViewPagerAdapter;
import com.melody.education.fragment.GrammarFragment;
import com.melody.education.fragment.LessonFragment;
import com.melody.education.fragment.VocabylaryFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by K53SV on 8/29/2016.
 */
public class LearningActivity extends BaseActivity {
    @BindView(R.id.viewpager_learning)
    ViewPager mViewPager;
    @BindView(R.id.btn1)
    ImageView btn1;
    @BindView(R.id.btn2)
    ImageView btn2;
    @BindView(R.id.btn3)
    ImageView btn3;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ViewPagerAdapter adapter;
    private int selectedIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
        ButterKnife.bind(this);
        retrieveExtras();
        setUpView();
        setTab();
    }

    private void retrieveExtras() {
        Bundle extras = getIntent().getExtras();
        selectedIndex = extras.getInt(LessonFragment.EXTRA_INDEX, 0);
    }

    private void setUpView() {
        setSupportActionBar(toolbar);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(LessonFragment.newInstance(selectedIndex), getString(R.string.fragment_lesson_title));
        adapter.addFragment(new VocabylaryFragment(), getString(R.string.fragment_vocabulary_title));
        adapter.addFragment(new GrammarFragment(), getString(R.string.fragment_grammar_title));
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);

        btn1.setImageResource(R.drawable.fill_circle);
        getSupportActionBar().setTitle(adapter.getPageTitle(0));
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
                // TODO Auto-generated method stub
                btn1.setImageResource(R.drawable.holo_circle);
                btn2.setImageResource(R.drawable.holo_circle);
                btn3.setImageResource(R.drawable.holo_circle);
                btnAction(position);
                getSupportActionBar().setTitle(adapter.getPageTitle(position));
            }

        });

    }

    private void btnAction(int action) {
        switch (action) {
            case 0:
                btn1.setImageResource(R.drawable.fill_circle);

                break;

            case 1:
                btn2.setImageResource(R.drawable.fill_circle);

                break;
            case 2:
                btn3.setImageResource(R.drawable.fill_circle);

                break;
        }
    }

}
