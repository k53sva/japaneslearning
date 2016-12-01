package com.melody.education.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by K53SV on 8/29/2016.
 */
public class BaseFragment extends Fragment {

    public void showAlertAction(Context c, BaseActivity.AlertListener listener, String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(c);
        builder1.setMessage(message);
        builder1.setCancelable(false);
        builder1.setPositiveButton("OK", (dialog, id) -> {
            listener.bundle(id);
            dialog.dismiss();
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public interface AlertListener {
        void bundle(int id);

    }
}
