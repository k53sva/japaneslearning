package com.melody.education.model;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;

import com.melody.education.R;
import com.melody.education.binding.fields.ObservableBoolean;

import java.io.IOException;

/**
 * Created by K53SV on 11/1/2016.
 */

public class WordOnReading {
    public int id;
    public String KanjiNumber;
    public String KanjiWords;
    public String English;
    public String KanjiLook;
    public String Sound;
    public boolean isSound = false;
    public final ObservableBoolean isLoading = new ObservableBoolean();
    {
        isLoading.set(false);
    }

    public void playAudio(View view) {
        view.setVisibility(View.GONE);
        isLoading.set(true);
        try {
            MediaPlayer mp = new MediaPlayer();
            mp.setDataSource(Sound);
            mp.setOnPreparedListener(m ->{
                isLoading.set(false);
                view.setVisibility(View.VISIBLE);
                mp.start();
                ((ImageView) view).setImageResource(R.drawable.playlistcore_ic_pause_black);
            });
            mp.setOnCompletionListener(m -> {
                m.reset();
                ((ImageView) view).setImageResource(R.drawable.playlistcore_ic_play_arrow_black);
            });
            mp.prepareAsync();

        } catch (IOException e) {
            isLoading.set(false);
            view.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
    }
}
