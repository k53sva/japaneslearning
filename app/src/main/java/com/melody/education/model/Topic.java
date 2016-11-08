package com.melody.education.model;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;

import com.melody.education.R;
import com.melody.education.binding.fields.ObservableBoolean;

import java.io.IOException;

import rx.Observable;

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
    public boolean isSound = false;
    public final ObservableBoolean isLoading = new ObservableBoolean();

    {
        isLoading.set(false);
    }

    public Topic() {
        isLoading.set(false);
    }

    public void playAudio(View view) {
        view.setVisibility(View.GONE);
        isLoading.set(true);
        try {
            MediaPlayer mp = new MediaPlayer();
            mp.setDataSource(AudioUrl);
            mp.setOnPreparedListener(m -> {
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