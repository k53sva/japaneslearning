package com.melody.education.model;

import com.melody.education.binding.fields.ObservableString;

/**
 * Created by K53SV on 11/8/2016.
 */

public class Notes {
    public int id;
    public String ChungID;
    public String Detail;
    public final ObservableString v = new ObservableString();

    public void setView(String detail) {
        v.set(detail);
    }
}
