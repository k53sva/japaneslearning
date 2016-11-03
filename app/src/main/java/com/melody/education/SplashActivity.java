package com.melody.education;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.annimon.stream.Stream;
import com.melody.education.net.FetchData;
import com.melody.education.ui.BaseActivity;
import com.melody.education.ui.MainActivity;
import com.melody.education.utils.DataHelper;

import java.io.File;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by K53SV on 8/31/2016.
 */
public class SplashActivity extends BaseActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    FetchData fetchData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        fetchData = new FetchData(this);
        String filePath = String.format("%s/%s", this.getExternalCacheDir(), DataHelper.DATABASE_KANJI);
        File file = new File(filePath);
        if (file.exists()) {
            delay(1, "database is exist");
            Log.e(TAG, filePath);
        } else {
            getData();
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
                .filter(Map.Entry::getValue)
                .toList()
                .filter(m -> m.size() == 3)
                .subscribe(m -> {
                    delay(1, "Download success");
                });
    }
}
