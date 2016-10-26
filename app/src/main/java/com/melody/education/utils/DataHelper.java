package com.melody.education.utils;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.annimon.stream.Optional;
import com.google.gson.Gson;
import com.melody.education.model.Conversation;

import org.json.JSONArray;
import org.json.JSONObject;

import rx.Observable;
import rx.schedulers.Schedulers;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by K53SV on 8/31/2016.
 */
public class DataHelper {
    private static final String TAG = DataHelper.class.getSimpleName();
    public static final String DATABASE_CONVERSATION = "conversation.sqlite";
    public static final String DATABASE_TOPICS = "topics.sqlite";
    public static final String TABLE_LESSON = "Lesson";
    public static final String TABLE_VOCABULARY = "Vocabulary";
    public static final String TABLE_NOTES = "Notes";
    public static final String TABLE_CONVERSATION = "Conversation";
    public static final String TABLE_TOPIC = "Topic";
    public static final String TABLE_TOPIC_TITLE = "TopicTitle";

    private Activity activity;
    private Gson gson = new Gson();

    public DataHelper(Activity activity) {
        this.activity = activity;
    }

    private synchronized JSONArray convertDatabaseToJson(String databaseName, String table) {
        String myPath = String.format("%s/%s", activity.getExternalCacheDir(), databaseName);
        SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        String searchQuery = "SELECT  * FROM " + table;
        Cursor cursor = myDataBase.rawQuery(searchQuery, null);
        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        myDataBase.close();
        return resultSet;
    }

    public <T> Observable<T> getData(String database, String table, Class<T> clazz) {
        return Observable.just(convertDatabaseToJson(database, table))
                .subscribeOn(Schedulers.io())
                .map(m -> gson.fromJson(m.toString(), clazz));
    }
}
