package com.melody.education.net;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.melody.education.utils.DataHelper;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

import rx.Observable;

/**
 * Created by K53SV on 9/12/2016.
 */
public class FetchData {
    public static final String ROOT_URL = "http://ahaheaven.esy.es/";
    public static final String PATH_DB = ROOT_URL + "japanesedb/";

    private Context mContext;

    public FetchData(Context mContext) {
        this.mContext = mContext;
    }

    public Observable<Boolean> getDataConversation() {

        return Observable.create(
                subscriber -> {
                    ThinDownloadManager downloadManager = new ThinDownloadManager(4);
                    String url = FetchData.PATH_DB + DataHelper.DATABASE_CONVERSATION;
                    //String url = "http://japaneselearning.comli.com/conversation.sqlite";
                    Log.e("URL", url);
                    Uri downloadUri = Uri.parse(url);
                    Uri destinationUri = Uri.parse(mContext.getExternalCacheDir().toString() + "/" + DataHelper.DATABASE_CONVERSATION);
                    Log.e("URI", destinationUri.toString());
                    DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
                            .addCustomHeader("Auth-Token", "YourTokenApiKey")
                            .setRetryPolicy(new DefaultRetryPolicy())
                            .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
                            .setDownloadListener(new DownloadStatusListener() {
                                @Override
                                public void onDownloadComplete(int id) {
                                    subscriber.onNext(true);
                                    subscriber.onCompleted();
                                }

                                @Override
                                public void onDownloadFailed(int id, int errorCode, String errorMessage) {
                                    subscriber.onError(new Exception(errorCode + ": " + errorMessage));
                                }

                                @Override
                                public void onProgress(int id, long totalBytes, long downlaodedBytes, int progress) {
                                }
                            });

                    downloadManager.add(downloadRequest);
                });
    }
}
