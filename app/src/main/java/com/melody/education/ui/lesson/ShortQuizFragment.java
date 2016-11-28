package com.melody.education.ui.lesson;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;
import com.melody.education.R;
import com.melody.education.adapter.ViewPagerAdapter;
import com.melody.education.ui.BaseFragment;
import com.melody.education.ui.lesson.viewmodel.KanjiQuizFragment;
import com.melody.education.ui.lesson.viewmodel.RomajiQuizFragment;
import com.melody.education.utils.LockableViewPager;


/**
 * Created by K53SV on 11/12/2016.
 */

public class ShortQuizFragment extends BaseFragment {
    private ViewPagerAdapter adapter;
    private LockableViewPager mViewPager;
    private Button btnChange;
    private boolean isKanji = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_short_quiz, container, false);
        mViewPager = (LockableViewPager) view.findViewById(R.id.viewpager_learning);
        btnChange = (Button) view.findViewById(R.id.btn_change);

        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new RomajiQuizFragment(), "");
        adapter.addFragment(new KanjiQuizFragment(), "");
        mViewPager.setAdapter(adapter);
        mViewPager.beginFakeDrag();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        RxView.clicks(btnChange)
                .subscribe(m -> {
                    if (isKanji) {
                        btnChange.setText("Kanji");
                        mViewPager.setCurrentItem(1);
                    } else {
                        btnChange.setText("Romaji");
                        mViewPager.setCurrentItem(0);
                    }
                    isKanji = !isKanji;
                });

    }
}
