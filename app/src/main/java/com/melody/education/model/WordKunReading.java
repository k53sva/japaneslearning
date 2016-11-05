package com.melody.education.model;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;

import com.melody.education.R;

import java.io.IOException;

/**
 * Created by K53SV on 11/1/2016.
 */

public class WordKunReading {
    public int id;
    public String KanjiNumber;
    public String KanjiWords;
    public String KanjiLook;
    public String English;
    public String Sound;

    public void playAudio(View view) {
        try {
            MediaPlayer mp = new MediaPlayer();
            mp.setDataSource(Sound);
            mp.setOnPreparedListener(m ->{
                mp.start();
                ((ImageView) view).setImageResource(R.drawable.playlistcore_ic_pause_black);
            });
            mp.setOnCompletionListener(m -> {
                m.reset();
                ((ImageView) view).setImageResource(R.drawable.playlistcore_ic_play_arrow_black);
            });
            mp.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
