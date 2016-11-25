package com.melody.education;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.melody.education.net.FetchData;
import com.melody.education.ui.BaseActivity;
import com.melody.education.ui.MainActivity;
import com.melody.education.utils.DataHelper;
import com.melody.education.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by K53SV on 8/31/2016.
 */
public class SplashActivity extends BaseActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private FetchData fetchData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //delay(1, "");
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.checkFileExits(String.format("%s/%s", this.getExternalCacheDir(), DataHelper.DATABASE_CONVERSATION))) {
            delay(1, "");
        } else {
            new FetchData(this).getDataConversation()
                    .filter(m -> m)
                    .subscribe(m -> delay(0, ""));
        }
    }

    private void delay(int s, String message) {
        new Handler().postDelayed(() -> {
            Log.v(TAG, message);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, s * 1000);
    }

    private void getData() {
        fetchData.getAllData()
                .map(HashMap::entrySet)
                .flatMapIterable(entries -> entries)
                .map(Map.Entry::getValue)
                .reduce(true, (x, y) -> x && y)
                .subscribe(m -> {
                    if (m)
                        delay(1, "Download success");
                    else
                        showAlertAction(this, id -> getData(), "Download failed! You want try download again?");
                });
    }

}
