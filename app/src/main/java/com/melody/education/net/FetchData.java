package com.melody.education.net;

import android.content.Context;
import android.net.Uri;

import com.melody.education.utils.DataHelper;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

import java.util.HashMap;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by K53SV on 9/12/2016.
 */
public class FetchData {
    public static final String ROOT_URL = "http://ahaheaven.esy.es/";
    private static final String PATH_DB = String.format("%sjapanesedb/", ROOT_URL);

    private Context mContext;

    public FetchData(Context mContext) {
        this.mContext = mContext;
    }

    private Observable<Boolean> getDataConversation() {
        return Observable.create(
                subscriber -> {
                    ThinDownloadManager downloadManager = new ThinDownloadManager(4);
                    String url = FetchData.PATH_DB + DataHelper.DATABASE_CONVERSATION;
                    Uri downloadUri = Uri.parse(url);
                    Uri destinationUri = Uri.parse(String.format("%s/%s", mContext.getExternalCacheDir(), DataHelper.DATABASE_CONVERSATION));
                    DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
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
                                    subscriber.onNext(false);
                                    subscriber.onCompleted();
                                }

                                @Override
                                public void onProgress(int id, long totalBytes, long bytes, int progress) {
                                }
                            });

                    downloadManager.add(downloadRequest);
                });
    }

    private Observable<Boolean> getDataTopic() {
        return Observable.create(
                subscriber -> {
                    ThinDownloadManager downloadManager = new ThinDownloadManager(4);
                    String url = FetchData.PATH_DB + DataHelper.DATABASE_TOPICS;
                    Uri downloadUri = Uri.parse(url);
                    Uri destinationUri = Uri.parse(String.format("%s/%s", mContext.getExternalCacheDir(), DataHelper.DATABASE_TOPICS));
                    DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
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
                                    subscriber.onNext(false);
                                    subscriber.onCompleted();
                                }

                                @Override
                                public void onProgress(int id, long totalBytes, long downlaodedBytes, int progress) {
                                }
                            });

                    downloadManager.add(downloadRequest);
                });
    }

    public Observable<HashMap<Integer, Boolean>> getAllData() {
        return Observable.zip(getDataConversation().subscribeOn(Schedulers.newThread()), getDataTopic().subscribeOn(Schedulers.newThread()), (d1, d2) -> {
            HashMap<Integer, Boolean> map = new HashMap<>();
            map.put(0, d1);
            map.put(1, d2);
            return map;
        });
    }
}
