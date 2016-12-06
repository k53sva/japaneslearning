package com.melody.education;

import android.app.Application;
import android.os.StrictMode;

import com.melody.education.manager.PlaylistManager;
import com.melody.education.model.QuizChoose;
import com.melody.education.utils.DataHelper;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.NoEncryption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class App extends Application {

    private static App application;
    private static PlaylistManager playlistManager;
    private static DataHelper dataHelper;
    private HashMap<String, HashMap<Integer, Integer>> checkRomaji = new HashMap<>();
    private HashMap<String, HashMap<Integer, Integer>> checkKanji = new HashMap<>();

    @Override
    public void onCreate() {
        enableStrictMode();
        super.onCreate();
        application = this;
        playlistManager = new PlaylistManager();
    }

    public synchronized int getQuizRomaji(String id, int position) {
        HashMap<Integer, Integer> q = checkRomaji.get(id);
        if (q == null) {
            HashMap<Integer, Integer> h = new HashMap<>();
            checkRomaji.put(id, h);
            return 0;
        } else {
            if (q.get(position) != null)
                return q.get(position);
            else
                return 0;
        }
    }

    public synchronized HashMap<String, HashMap<Integer, Integer>> getCheckRomaji() {
        return checkRomaji;
    }

    public synchronized HashMap<Integer, Integer> getListRomaji(String id) {
        if (checkRomaji.get(id) == null) {
            HashMap<Integer, Integer> h = new HashMap<>();
            checkRomaji.put(id, h);
        }
        return checkRomaji.get(id);
    }

    public synchronized HashMap<String, HashMap<Integer, Integer>> getCheckKanji() {
        return checkKanji;
    }

    public synchronized int getQuizKanji(String id, int position) {
        HashMap<Integer, Integer> q = checkKanji.get(id);
        if (q == null) {
            HashMap<Integer, Integer> h = new HashMap<>();
            checkKanji.put(id, h);
            return 0;
        } else {
            if (q.get(position) != null)
                return q.get(position);
            else
                return 0;
        }
    }

    public synchronized HashMap<Integer, Integer> getListKanji(String id) {
        if (checkKanji.get(id) == null) {
            HashMap<Integer, Integer> h = new HashMap<>();
            checkKanji.put(id, h);
        }
        return checkKanji.get(id);
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
