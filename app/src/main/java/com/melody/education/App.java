package com.melody.education;

import android.app.Application;
import android.os.StrictMode;

import com.melody.education.manager.PlaylistManager;
import com.melody.education.model.QuizChoose;
import com.melody.education.utils.DataHelper;

import java.util.HashMap;


public class App extends Application {

    private static App application;
    private static PlaylistManager playlistManager;
    private static DataHelper dataHelper;
    private HashMap<String, QuizChoose> checkRomaji = new HashMap<>();
    private HashMap<String, QuizChoose> checkKanji = new HashMap<>();

    @Override
    public void onCreate() {
        enableStrictMode();
        super.onCreate();
        application = this;
        playlistManager = new PlaylistManager();
    }

    public synchronized HashMap<String, QuizChoose> getCheckRomaji() {
        return checkRomaji;
    }

    public synchronized HashMap<String, QuizChoose> getCheckKanji() {
        return checkKanji;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        application = null;
        playlistManager = null;
    }

    public static DataHelper getDataHelper() {
        if (dataHelper == null) {
            dataHelper = new DataHelper(getApplication());
        }
        return dataHelper;
    }

    public static PlaylistManager getPlaylistManager() {
        return playlistManager;
    }

    public static App getApplication() {
        return application;
    }

    private void enableStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build());
    }
}
