package com.melody.education;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.melody.education.model.Version;
import com.melody.education.net.FetchData;
import com.melody.education.ui.BaseActivity;
import com.melody.education.ui.MainActivity;
import com.melody.education.utils.DataCache;
import com.melody.education.utils.DataHelper;
import com.melody.education.utils.Utils;

import cz.msebera.android.httpclient.Header;
import rx.Observable;

/**
 * Created by K53SV on 8/31/2016.
 */
public class SplashActivity extends BaseActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        float ver = preferences.getFloat(Version.CONVERSATION_KEY, 0);
        getVersion()
                .doOnNext(v -> DataCache.getInstance().push(v))
                .map(m -> m.conversation).subscribe(v -> checkVersion(v, ver), throwable ->
                showAlertAction(this, id -> startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS))
                        , "No connection. Please check connect internet"));

    }

    private void checkVersion(float v1, float v2) {
        if (v1 == v2) {
            if (Utils.checkFileExits(String.format("%s/%s", this.getExternalCacheDir(), DataHelper.DATABASE_CONVERSATION))) {
                delay(0, "");
                Log.e(TAG, "V1=V2 and exits");
            } else {
                getDataConversation(v1);
                Log.e(TAG, "V1=V2 and not exits");
            }
        } else {
            Log.e(TAG, "V1!=V2");
            getDataConversation(v1);
        }
    }

    private void getDataConversation(float v) {
        new FetchData(this).getDataConversation()
                .subscribe(m -> {
                    if (m) {
                        delay(0, "");
                        editor.putFloat(Version.CONVERSATION_KEY, v);
                        editor.commit();
                    } else
                        showAlertAction(this, id -> startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS))
                                , "No connection. Please check connect internet");
                });
    }


    private void delay(int s, String message) {
        new Handler().postDelayed(() -> {
            Log.v(TAG, message);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, s * 1000);
    }

    private Observable<Version> getVersion() {
        return Observable.create(s -> {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(FetchData.PATH_VERSION, new TextHttpResponseHandler() {

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    s.onError(throwable);
                    s.onCompleted();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Version version = new Gson().fromJson(responseString, Version.class);
                    s.onNext(version);
                    s.onCompleted();
                }
            });
        });

    }

}
