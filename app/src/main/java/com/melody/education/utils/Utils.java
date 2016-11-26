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
    public static final String TAG = Utils.class.getName();
    public static final String PRF_LESSON_FINAL = "PRF_LESSON_FINAL";

    public static boolean checkFinal(int count, int total) {
        if ((count / total) * 100 > 80)
            return true;
        return false;
    }

    public static void startFragment(FragmentActivity context, Fragment fragment) {
        FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_body, fragment).commit();
    }

    public static int dpToPx(Context c, int dp) {
        Resources r = c.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static boolean checkFileExits(String path) {
        File file = new File(path);
        Log.e(TAG, path + ": " + file.exists());
        return file.exists();
    }
}
