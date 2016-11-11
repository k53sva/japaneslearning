package com.melody.education.ui.lesson;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.melody.education.R;
import com.melody.education.adapter.ViewPagerAdapter;
import com.melody.education.model.Dialogue1;
import com.melody.education.model.Dialogue2;
import com.melody.education.net.FetchData;
import com.melody.education.ui.BaseFragment;
import com.melody.education.utils.DataHelper;
import com.melody.education.utils.Utils;
import com.viewpagerindicator.PageIndicator;

import nl.changer.audiowife.AudioWife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by K53SV on 11/10/2016.
 */

public class DialogFragment extends BaseFragment {
    public static final String EXTRA_INDEX = "EXTRA_INDEX";
    private ViewPagerAdapter adapter;
    private ViewPager mViewPager;
    private PageIndicator titleIndicator;
    private View mPlayMedia;
    private View mPauseMedia;
    private SeekBar mMediaSeekBar;
    private TextView mRunTime;
    private TextView mTotalTime;
    private Uri mUri;
    private String ChungID;

    private LessonDialogFragment dialog1 = new LessonDialogFragment();
    private LessonDialog2Fragment dialog2 = new LessonDialog2Fragment();

    public static DialogFragment newInstance(String id) {
        DialogFragment fragment = new DialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_INDEX, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void retrieveExtras() {
        if (getArguments() != null) {
            ChungID = getArguments().getString(EXTRA_INDEX);
        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        retrieveExtras();
        mPlayMedia = view.findViewById(R.id.play);
        mPauseMedia = view.findViewById(R.id.pause);
        mMediaSeekBar = (SeekBar) view.findViewById(R.id.media_seekbar);
        mRunTime = (TextView) view.findViewById(R.id.run_time);
        mTotalTime = (TextView) view.findViewById(R.id.total_time);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager_learning);
        titleIndicator = (PageIndicator) view.findViewById(R.id.indicator);
        adapter = new ViewPagerAdapter(getChildFragmentManager());

        progressData();
        setPlayer(0);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                AudioWife.getInstance().release();
                setPlayer(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setPlayer(int positon) {
        if (positon == 0)
            getData("le10_v_scall.mp3");
        else
            getData("le11_v_scall.mp3");
    }

    private void progressData() {
        DataHelper helper = new DataHelper(getActivity());
        Observable<Dialogue1> z1 = helper.getData(DataHelper.DATABASE_LESSON, DataHelper.TABLE_DIAGLOGUE_1, Dialogue1[].class)
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .filter(m -> m.ChungID.equals(ChungID))
                .doOnNext(m -> Log.e("TAG", ChungID))
                .take(1);

        Observable<Dialogue2> z2 = helper.getData(DataHelper.DATABASE_LESSON, DataHelper.TABLE_DIAGLOGUE_2, Dialogue2[].class)
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .filter(m -> m.ChungID.equals(ChungID))
                .take(1);

        Observable.zip(z1, z2, (m1, m2) -> {
            dialog1.setModel(m1);
            dialog2.setModel(m2);
            return true;
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(m -> {
                    adapter.addFragment(dialog1, "");
                    adapter.addFragment(dialog2, "");
                    mViewPager.setAdapter(adapter);
                    titleIndicator.setViewPager(mViewPager);
                });


    }

    private void getData(String name) {
        String file = String.format("%s/%s", getActivity().getExternalCacheDir(), name);
        if (!Utils.checkFileExits(file))
            new FetchData(getContext()).getAudio("conversation/", name)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(m -> mUri = m)
                    .subscribe(m -> setPlayMedia());
        else {
            mUri = Uri.parse(file);
            setPlayMedia();
        }
    }

    public void setPlayMedia() {
        AudioWife.getInstance()
                .init(getActivity(), mUri)
                .setPlayView(mPlayMedia)
                .setPauseView(mPauseMedia)
                .setSeekBar(mMediaSeekBar)
                .setRuntimeView(mRunTime)
                .setTotalTimeView(mTotalTime);

        AudioWife.getInstance().addOnCompletionListener(mp -> {
        });

        AudioWife.getInstance().addOnPlayClickListener(v -> {
        });
    }
}
