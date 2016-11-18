package com.melody.education.ui.lesson;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melody.education.R;
import com.melody.education.adapter.ViewPagerAdapter;
import com.melody.education.ui.BaseFragment;
import com.melody.education.ui.lesson.viewmodel.KanjiQuizFragment;
import com.melody.education.ui.lesson.viewmodel.RomajiQuizFragment;
import com.viewpagerindicator.PageIndicator;


/**
 * Created by K53SV on 11/12/2016.
 */

public class ShortQuizFragment extends BaseFragment {
    private ViewPagerAdapter adapter;
    private ViewPager mViewPager;
    private PageIndicator titleIndicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_short_quiz, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager_learning);
        titleIndicator = (PageIndicator) view.findViewById(R.id.indicator);
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new RomajiQuizFragment(), "");
        adapter.addFragment(new KanjiQuizFragment(), "");
        mViewPager.setAdapter(adapter);
        titleIndicator.setViewPager(mViewPager);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
