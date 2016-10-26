package com.melody.education.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.gson.Gson;
import com.melody.education.App;
import com.melody.education.R;
import com.melody.education.adapter.ConversationAdapter;
import com.melody.education.adapter.ConversationFragmentAdapter;
import com.melody.education.data.MediaItem;
import com.melody.education.manager.PlaylistManager;
import com.melody.education.model.Conversation;
import com.melody.education.ui.BaseFragment;
import com.melody.education.utils.DataHelper;
import com.melody.education.utils.GridSpacingItemDecoration;
import com.melody.education.utils.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by K53SV on 8/29/2016.
 */
public class ConversationFragment extends BaseFragment implements PlaylistListener<MediaItem>, ProgressListener {
    public static final String EXTRA_INDEX = "EXTRA_INDEX";
    public static final int PLAYLIST_ID = 4;
    private ProgressBar loadingBar;
    private ImageButton previousButton;
    private ImageButton playPauseButton;
    private ImageButton nextButton;
    private TextView currentPositionView;
    private TextView durationView;
    private SeekBar seekBar;
    private RecyclerView recyclerView;

    private boolean shouldSetDuration;
    private boolean userInteracting;
    private int selectedIndex = 0;
    private PlaylistManager playlistManager;
    private ConversationFragmentAdapter adapter;

    public static ConversationFragment newInstance(int index) {
        ConversationFragment fragment = new ConversationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_INDEX, index);
        fragment.setArguments(bundle);

        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);
        retrieveExtras();
        init(view);
        getData();
        return view;
    }

    private void getData() {
        Gson gson = new Gson();
        DataHelper helper = new DataHelper(getActivity());
        Observable.just(helper.convertDatabaseToJson(DataHelper.DATABASE_CONVERSATION, DataHelper.TABLE_CONVERSATION))
                .subscribeOn(Schedulers.io())
                .map(m -> gson.fromJson(m.toString(), Conversation[].class))
                .flatMap(Observable::from)
                .filter(m -> m.ChungID.equals(ConversationAdapter.conversationList.get(selectedIndex).ChungID))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(m -> adapter.setModel(m));

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
    }

    @Override
    public boolean onPlaylistItemChanged(MediaItem currentItem, boolean hasNext, boolean hasPrevious) {
        shouldSetDuration = true;
        nextButton.setEnabled(hasNext);
        previousButton.setEnabled(hasPrevious);

        return true;
    }

    @Override
    public boolean onPlaybackStateChanged(@NonNull PlaylistServiceCore.PlaybackState playbackState) {
        switch (playbackState) {
            case STOPPED:
                playlistManager.play(0, true);
                //getActivity().finish();
                break;

            case RETRIEVING:
            case PREPARING:
            case SEEKING:
                restartLoading();
                break;

            case PLAYING:
                doneLoading(true);
                break;

            case PAUSED:
                doneLoading(false);
                break;

            default:
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

    private boolean setupPlaylistManager() {
        playlistManager = App.getPlaylistManager();

        //There is nothing to do if the currently playing values are the same
        if (playlistManager.getId() == PLAYLIST_ID) {
            return false;
        }

        List<MediaItem> mediaItems = new LinkedList<>();
       /* for (Conversation conversation : ConversationAdapter.lessonArrayList) {
            MediaItem mediaItem = new MediaItem(conversation, true);
            mediaItems.add(mediaItem);
        }*/

        MediaItem mediaItem = new MediaItem(ConversationAdapter.conversationList.get(selectedIndex), true);
        mediaItems.add(mediaItem);
        playlistManager.setParameters(mediaItems, selectedIndex);
        playlistManager.setId(PLAYLIST_ID);

        return true;
    }

    private void init(View view) {
        retrieveViews(view);
        setupListeners();

        boolean generatedPlaylist = setupPlaylistManager();
        startPlayback(generatedPlaylist);
    }

    private void retrieveViews(View view) {
        loadingBar = (ProgressBar) view.findViewById(R.id.audio_player_loading);

        currentPositionView = (TextView) view.findViewById(R.id.audio_player_position);
        durationView = (TextView) view.findViewById(R.id.audio_player_duration);

        seekBar = (SeekBar) view.findViewById(R.id.audio_player_seek);

        previousButton = (ImageButton) view.findViewById(R.id.audio_player_previous);
        playPauseButton = (ImageButton) view.findViewById(R.id.audio_player_play_pause);
        nextButton = (ImageButton) view.findViewById(R.id.audio_player_next);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_conversation);
        adapter = new ConversationFragmentAdapter(getActivity(), new ArrayList<>());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(10, Utils.dpToPx(getActivity(), 1), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

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
        int resId = isPlaying ? R.drawable.playlistcore_ic_pause_white : R.drawable.playlistcore_ic_play_arrow_white;
        playPauseButton.setImageResource(resId);
    }

    public void loadCompleted() {
        playPauseButton.setVisibility(View.VISIBLE);
        previousButton.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);

        loadingBar.setVisibility(View.INVISIBLE);

    }

    public void restartLoading() {
        playPauseButton.setVisibility(View.GONE);
        previousButton.setVisibility(View.INVISIBLE);
        nextButton.setVisibility(View.GONE);

        loadingBar.setVisibility(View.VISIBLE);
    }

    private void setDuration(long duration) {
        seekBar.setMax((int) duration);
        durationView.setText(TimeFormatUtil.formatMs(duration));
    }

    private void setupListeners() {
        seekBar.setOnSeekBarChangeListener(new SeekBarChanged());

        previousButton.setOnClickListener(v -> playlistManager.invokePrevious());
        playPauseButton.setOnClickListener(v -> playlistManager.invokePausePlay());
        nextButton.setOnClickListener(v -> playlistManager.invokeNext());
    }

    private void retrieveExtras() {
        if (getArguments() != null) {
            selectedIndex = getArguments().getInt(EXTRA_INDEX, 0);
        }
    }

    private void startPlayback(boolean forceStart) {
        //If we are changing audio files, or we haven't played before then start the playback
        if (forceStart || playlistManager.getCurrentPosition() != selectedIndex) {
            playlistManager.setCurrentPosition(selectedIndex);
            playlistManager.play(0, false);
        }
    }

    private class SeekBarChanged implements SeekBar.OnSeekBarChangeListener {
        private int seekPosition = -1;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (!fromUser) {
                return;
            }

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


