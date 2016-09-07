package com.melody.education;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String filePath = this.getExternalCacheDir().toString() + "/" + DataHelper.DATABASE_NAME;
        File file = new File(filePath);
        if (file.exists()) {
            delay(1, "database is exist");
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
        ThinDownloadManager downloadManager = new ThinDownloadManager(4);
        String url = "http://japaneselearning.comli.com/data.sqlite";
        Uri downloadUri = Uri.parse(url);
        Uri destinationUri = Uri.parse(this.getExternalCacheDir().toString() + "/data.sqlite");
        Log.e("URI", destinationUri.toString());
        DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
                .addCustomHeader("Auth-Token", "YourTokenApiKey")
                .setRetryPolicy(new DefaultRetryPolicy())
                .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
                .setDownloadListener(new DownloadStatusListener() {
                    @Override
                    public void onDownloadComplete(int id) {
                        Log.e("Comple", id + "");
                        delay(1, "Download data complete");
                    }

                    @Override
                    public void onDownloadFailed(int id, int errorCode, String errorMessage) {
                        Log.e("Error", errorMessage);
                        Toast.makeText(SplashActivity.this, "Download Data Failed", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onProgress(int id, long totalBytes, long downlaodedBytes, int progress) {
                        if ((totalBytes - downlaodedBytes) / totalBytes == 0.5)
                            Log.e("Progress", id + "");
                    }
                });

        downloadManager.add(downloadRequest);

    }
}
