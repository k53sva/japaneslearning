package com.melody.education;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.melody.education.net.FetchData;
import com.melody.education.ui.BaseActivity;
import com.melody.education.ui.MainActivity;
import com.melody.education.utils.DataHelper;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

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
        String filePath = this.getExternalCacheDir().toString() + "/" + DataHelper.DATABASE_CONVERSATION;
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
        fetchData.getDataConversation()
                .retry(2)
                .filter(r -> true)
                .subscribe(r -> delay(1, "Success"),
                        e -> Log.e(TAG, e.getMessage()));
    }
}
