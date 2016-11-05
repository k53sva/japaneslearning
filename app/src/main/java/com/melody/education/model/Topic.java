package com.melody.education.model;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;

import com.melody.education.R;

import java.io.IOException;

/**
 * Created by K53SV on 9/12/2016.
 */
public class Topic {
    public String LessonName;
    public String Anh;
    public String Nhat;
    public String Romaji;
    public String Detail;
    public String ImageUrl;
    public String ChungID;
    public String AudioUrl;

    public void playAudio(View view) {
        try {
            MediaPlayer mp = new MediaPlayer();
            mp.setDataSource(AudioUrl);
            mp.setOnPreparedListener(m -> {
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