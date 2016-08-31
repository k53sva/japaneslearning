package com.melody.education.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.melody.education.R;

/**
 * Created by K53SV on 8/30/2016.
 */
public class UserSettingActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // add the xml resource
        addPreferencesFromResource(R.xml.user_settings);

    }

}
