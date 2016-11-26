package com.melody.education.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melody.education.R;
import com.melody.education.adapter.ViewPagerAdapter;
import com.melody.education.ui.syllabaries.HiraganaFragment;
import com.melody.education.ui.syllabaries.KatakanaFragment;
import com.melody.education.utils.Utils;

/**
 * Created by K53SV on 8/29/2016.
 */
public class SyllabariesFragment extends BaseFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_syllabaries, container, false);
        ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        AppBarLayout appBarLayout = ((MainActivity) getActivity()).appBarLayout;
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        lp.height = Utils.dpToPx(getActivity(), 100);
        appBarLayout.setLayoutParams(lp);
        ((MainActivity) getActivity()).tvDes.setVisibility(View.GONE);

        return v;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new HiraganaFragment(), "HIRAGANA");
        adapter.addFragment(new KatakanaFragment(), "KATAKANA");
        viewPager.setAdapter(adapter);
    }
}
