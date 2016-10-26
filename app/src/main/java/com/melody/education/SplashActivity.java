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

import java.io.File;

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
        String filePath = this.getExternalCacheDir() + "/" + DataHelper.DATABASE_TOPICS;
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
        fetchData.getAllData().subscribe(m -> {
            for (int i = 0; i < m.size(); i++) {
                Boolean data = m.get(i);
                Log.e(TAG, i + ": " + data);
            }
            delay(1, "Success");
        });
    }
}
