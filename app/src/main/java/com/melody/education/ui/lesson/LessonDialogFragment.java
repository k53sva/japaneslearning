package com.melody.education.ui.lesson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.melody.education.R;
import com.melody.education.model.Dialogue1;
import com.melody.education.ui.BaseFragment;

/**
 * Created by K53SV on 8/29/2016.
 */
public class LessonDialogFragment extends BaseFragment {
    public static final String TAG = LessonDialogFragment.class.getSimpleName();
    private TextView tvDialog, tvTranslate, tvLook;
    private Dialogue1 dialogue1;
    private TextView tvChange;
    private boolean isKanji = true;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson_dialog, container, false);
        retrieveViews(view);
        setView(dialogue1);
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
                        tvDialog.setText(dialogue1.Kanji1);
                    } else {
                        tvChange.setText("Romaji");
                        tvDialog.setText(dialogue1.Romaji1);
                    }
                    isKanji = !isKanji;
                });
    }

    private void setView(Dialogue1 m) {
        tvDialog.setText(m.Romaji1);
        tvTranslate.setText(m.Translate1);
        tvLook.setText(m.Look1);
    }

    public void setModel(Dialogue1 m) {
        this.dialogue1 = m;
    }

    private void retrieveViews(View view) {
        tvDialog = (TextView) view.findViewById(R.id.tv_dialog);
        tvTranslate = (TextView) view.findViewById(R.id.tv_translate);
        tvLook = (TextView) view.findViewById(R.id.tv_look);
        tvChange = (TextView) view.findViewById(R.id.tv_change);
    }
}


