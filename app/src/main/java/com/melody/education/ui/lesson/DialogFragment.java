package com.melody.education.ui.lesson;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.devbrackets.android.exomedia.util.TimeFormatUtil;
import com.devbrackets.android.playlistcore.event.MediaProgress;
import com.devbrackets.android.playlistcore.event.PlaylistItemChange;
import com.devbrackets.android.playlistcore.listener.PlaylistListener;
import com.devbrackets.android.playlistcore.listener.ProgressListener;
import com.devbrackets.android.playlistcore.service.PlaylistServiceCore;
import com.melody.education.App;
import com.melody.education.R;
import com.melody.education.adapter.ViewPagerAdapter;
import com.melody.education.data.MediaItem;
import com.melody.education.manager.PlaylistManager;
import com.melody.education.model.Conversation;
import com.melody.education.model.Dialogue1;
import com.melody.education.model.Dialogue2;
import com.melody.education.ui.BaseFragment;
import com.melody.education.utils.DataHelper;
import com.viewpagerindicator.PageIndicator;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by K53SV on 11/10/2016.
 */

public class DialogFragment extends BaseFragment implements PlaylistListener<MediaItem>, ProgressListener {
    public static final String TAG = DialogFragment.class.getSimpleName();
    public static final String EXTRA_INDEX = "EXTRA_INDEX";
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_IMAGE = "EXTRA_IMAGE";
    private ViewPagerAdapter adapter;
    private ViewPager mViewPager;
    private PageIndicator titleIndicator;
    private String ChungID;
    private String title;
    private String image;

    private ProgressBar loadingBar;
    private ImageButton playPauseButton;
    private TextView currentPositionView;
    private TextView durationView;
    private SeekBar seekBar;

    private boolean shouldSetDuration;
    private boolean userInteracting;
    private PlaylistManager playlistManager;

    private LessonDialogFragment dialog1 = new LessonDialogFragment();
    private LessonDialog2Fragment dialog2 = new LessonDialog2Fragment();

    public static DialogFragment newInstance(String id, String title, String image) {
        DialogFragment fragment = new DialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_INDEX, id);
        bundle.putString(EXTRA_TITLE, title);
        bundle.putString(EXTRA_IMAGE, image);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void retrieveExtras() {
        if (getArguments() != null) {
            ChungID = getArguments().getString(EXTRA_INDEX);
            title = getArguments().getString(EXTRA_TITLE);
            image = getArguments().getString(EXTRA_IMAGE);
        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        retrieveExtras();
        loadingBar = (ProgressBar) view.findViewById(R.id.audio_player_loading);
        currentPositionView = (TextView) view.findViewById(R.id.audio_player_position);
        durationView = (TextView) view.findViewById(R.id.audio_player_duration);
        seekBar = (SeekBar) view.findViewById(R.id.audio_player_seek);
        playPauseButton = (ImageButton) view.findViewById(R.id.audio_player_play_pause);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager_learning);
        titleIndicator = (PageIndicator) view.findViewById(R.id.indicator);
        adapter = new ViewPagerAdapter(getChildFragmentManager());

        progressData();
        return view;
    }


    private void progressData() {
        Observable<Dialogue1> z1 = App.getDataHelper().getData(DataHelper.DATABASE_LESSON, DataHelper.TABLE_DIALOGUE_1, Dialogue1[].class)
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .filter(m -> m.ChungID.equals(ChungID))
                .take(1);

        Observable<Dialogue2> z2 = App.getDataHelper().getData(DataHelper.DATABASE_LESSON, DataHelper.TABLE_DIALOGUE_2, Dialogue2[].class)
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
                    boolean generatedPlaylist = setupPlaylistManager(dialog1.getModel().Audio1);
                    startPlayback(generatedPlaylist);
                    setupListeners();
                });


    }

    public void onPause() {
        super.onPause();
        playlistManager.unRegisterPlaylistListener(this);
        playlistManager.unRegisterProgressListener(this);
    }

    public void onResume() {
        super.onResume();
        playlistManager = App.getPlaylistManager();
        playlistManager.registerPlaylistListener(this);
        playlistManager.registerProgressListener(this);
        updateCurrentPlaybackInformation();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    boolean generatedPlaylist = setupPlaylistManager(dialog2.getModel().Audio2);
                    startPlayback(generatedPlaylist);
                    setupListeners();
                } else {
                    boolean generatedPlaylist = setupPlaylistManager(dialog1.getModel().Audio1);
                    startPlayback(generatedPlaylist);
                    setupListeners();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onPlaylistItemChanged(MediaItem currentItem, boolean hasNext, boolean hasPrevious) {
        shouldSetDuration = true;
        return true;
    }

    @Override
    public boolean onPlaybackStateChanged(@NonNull PlaylistServiceCore.PlaybackState playbackState) {
        switch (playbackState) {
            case STOPPED:
                playlistManager.play(0, false);
                break;

            case RETRIEVING:
                Log.e(TAG, "RETRIEVING");
                break;
            case PREPARING:

                Log.e(TAG, "PREPARING");
                break;
            case SEEKING:
                Log.e(TAG, "SEEKING");
                break;

            case PLAYING:
                doneLoading(true);
                Log.e(TAG, "PLAYING");
                break;

            case PAUSED:
                doneLoading(false);
                Log.e(TAG, "PAUSED");
                break;

            default:
                Log.e(TAG, "DEFAULT");
                break;
        }

        return true;
    }

    @Override
    public boolean onProgressUpdated(@NonNull MediaProgress progress) {
        if (shouldSetDuration && progress.getDuration() > 0) {
            shouldSetDuration = false;
            setDuration(progress.getDuration());
        }

        if (!userInteracting) {
            seekBar.setSecondaryProgress((int) (progress.getDuration() * progress.getBufferPercentFloat()));
            seekBar.setProgress((int) progress.getPosition());
            currentPositionView.setText(TimeFormatUtil.formatMs(progress.getPosition()));
        }

        return true;
    }

    private boolean setupPlaylistManager(String s1) {
        Conversation c1 = new Conversation();
        c1.Audio = s1;
        c1.Title = title;
        c1.Anh = image;

        playlistManager = App.getPlaylistManager();
        playlistManager.reset();
        if (playlistManager.getId() == 3) {
            return false;
        }

        List<MediaItem> mediaItems = new LinkedList<>();
        mediaItems.add(new MediaItem(c1, true));
        playlistManager.setParameters(mediaItems, 0);
        playlistManager.setId(0);
        return true;
    }


    private void updateCurrentPlaybackInformation() {
        PlaylistItemChange<MediaItem> itemChangedEvent = playlistManager.getCurrentItemChange();
        if (itemChangedEvent != null) {
            onPlaylistItemChanged(itemChangedEvent.getCurrentItem(), itemChangedEvent.hasNext(), itemChangedEvent.hasPrevious());
        }

        PlaylistServiceCore.PlaybackState currentPlaybackState = playlistManager.getCurrentPlaybackState();
        if (currentPlaybackState != PlaylistServiceCore.PlaybackState.STOPPED) {
            onPlaybackStateChanged(currentPlaybackState);
        }

        MediaProgress progressEvent = playlistManager.getCurrentProgress();
        if (progressEvent != null) {
            onProgressUpdated(progressEvent);
        }
    }

    private void doneLoading(boolean isPlaying) {
        loadCompleted();
        updatePlayPauseImage(isPlaying);
    }

    private void updatePlayPauseImage(boolean isPlaying) {
        int resId = isPlaying ? R.drawable.playlistcore_ic_pause_black : R.drawable.playlistcore_ic_play_arrow_black;
        playPauseButton.setImageResource(resId);
    }

    public void loadCompleted() {
        playPauseButton.setVisibility(View.VISIBLE);
        loadingBar.setVisibility(View.INVISIBLE);

    }


    private void setDuration(long duration) {
        seekBar.setMax((int) duration);
        durationView.setText(TimeFormatUtil.formatMs(duration));
    }

    private void setupListeners() {
        seekBar.setOnSeekBarChangeListener(new SeekBarChanged());
        playPauseButton.setOnClickListener(v -> playlistManager.invokePausePlay());
    }


    private void startPlayback(boolean forceStart) {
        if (forceStart) {
            playlistManager.setCurrentPosition(0);
            playlistManager.play(0, false);
        }
    }

    private class SeekBarChanged implements SeekBar.OnSeekBarChangeListener {
        private int seekPosition = -1;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            seekPosition = progress;
            currentPositionView.setText(TimeFormatUtil.formatMs(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            userInteracting = true;

            seekPosition = seekBar.getProgress();
            playlistManager.invokeSeekStarted();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            userInteracting = false;

            //noinspection Range - seekPosition won't be less than 0
            playlistManager.invokeSeekEnded(seekPosition);
            seekPosition = -1;
        }
    }


}
