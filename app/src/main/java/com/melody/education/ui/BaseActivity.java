package com.melody.education.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.melody.education.R;

/**
 * Created by K53SV on 8/29/2016.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        // Enclose everything in a try block so we can just
        // use the default view if anything goes wrong.
        try {
            // Get the font size value from SharedPreferences.
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

            // Get the font size option.  We use "FONT_SIZE" as the key.
            // Make sure to use this key when you set the value in SharedPreferences.
            // We specify "Medium" as the default value, if it does not exist.
            String fontSizePref = sharedPrefs.getString("prefUpdateFrequency", "Medium");

            // Select the proper theme ID.
            // These will correspond to your theme names as defined in themes.xml.
            int themeID = R.style.FontSizeMedium;
            if (fontSizePref == "Small") {
                themeID = R.style.FontSizeSmall;
            } else if (fontSizePref == "Large") {
                themeID = R.style.FontSizeLarge;
            }
            // Set the theme for the activity.
            setTheme(themeID);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
}
