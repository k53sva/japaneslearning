package com.melody.education.utils;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.TypedValue;

import com.melody.education.R;
import com.melody.education.net.FetchData;

import java.io.File;
import java.net.InetAddress;

/**
 * Created by K53SV on 8/29/2016.
 */
public class Utils {
    public static final String PRF_KEY_COUNT = "PRF_KEY_COUNT";
    public static final String PRF_KEY_TOTAL = "PRF_KEY_TOTAL";
    public static final String PRF_KEY_ROMAJI = "PRF_KEY_ROMAJI";
    public static final String PRF_KEY_KANJI = "PRF_KEY_KANJI";

    public static final String PACKAGE_NAME = "com.melody.education";


    public static void startFragment(FragmentActivity context, Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = context.getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.frame_body, fragment, fragmentTag);
            ft.commit();
        }
    }

    /**
     * Converting dp to pixel
     */
    public static int dpToPx(Context c, int dp) {
        Resources r = c.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    /**
     * Move file
     */
    public void moveFile() {
        File from = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/kaic1/imagem.jpg");
        File to = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/kaic2/imagem.jpg");
        from.renameTo(to);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean checkFileExits(String path) {
        File file = new File(path);
        Log.e("TAG", path + ": " + file.exists());
        return file.exists();
    }
}
