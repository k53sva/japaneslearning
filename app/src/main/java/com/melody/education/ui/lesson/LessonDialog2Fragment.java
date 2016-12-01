package com.melody.education.ui.lesson;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.melody.education.R;
import com.melody.education.model.Dialogue1;
import com.melody.education.model.Dialogue2;
import com.melody.education.net.FetchData;
import com.melody.education.ui.BaseFragment;
import com.melody.education.utils.DataHelper;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by K53SV on 8/29/2016.
 */
public class LessonDialog2Fragment extends BaseFragment {
    public static final String TAG = LessonDialog2Fragment.class.getSimpleName();
    private TextView tvDialog, tvTranslate, tvLook;
    private Dialogue2 dialogue2;
    private TextView tvChange;
    private boolean isKanji = true;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson_dialog, container, false);
        retrieveViews(view);
        setView(dialogue2);
        return view;
    }

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        RxView.clicks(tvChange)
                .subscribe(m -> {
                    if (isKanji) {
                        tvChange.setText("Kanji");
                        tvDialog.setText(dialogue2.Kanji2);
                    } else {
                        tvChange.setText("Romaji");
                        tvDialog.setText(dialogue2.Romaji2);
                    }
                    isKanji = !isKanji;
                });
    }

    private void setView(Dialogue2 m) {
        tvDialog.setText(m.Romaji2);
        tvTranslate.setText(m.Translate2);
        tvLook.setText(m.Look2);
    }

    public void setModel(Dialogue2 m) {
        this.dialogue2 = m;
        m.Audio2 = FetchData.ROOT_URL + "lesson/" + m.Audio2;
    }

    public Dialogue2 getModel() {
        return this.dialogue2;
    }


    private void retrieveViews(View view) {
        tvDialog = (TextView) view.findViewById(R.id.tv_dialog);
        tvTranslate = (TextView) view.findViewById(R.id.tv_translate);
        tvLook = (TextView) view.findViewById(R.id.tv_look);
        tvChange = (TextView) view.findViewById(R.id.tv_change);

    }
}


