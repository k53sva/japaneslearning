package com.melody.education.ui.kanji.viewmodel;

import com.melody.education.binding.fields.ObservableString;

/**
 * Created by K53SV on 11/3/2016.
 */

public class ItemKanjiLevel {
    public static final String TAG = ItemKanjiLevel.class.getSimpleName();
    public final ObservableString name = new ObservableString();

    public ItemKanjiLevel(String title) {
        name.set(title);
    }
}
