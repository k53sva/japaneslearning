package com.melody.education.utils;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by K53SV on 8/31/2016.
 */
public class DataHelper {
    private static final String TAG = DataHelper.class.getSimpleName();
    public static String DATABASE_CONVERSATION = "conversation.sqlite";
    public static String DATABASE_TOPICS = "topics.sqlite";

    public static String TABLE_LESSON = "Lesson";
    public static String TABLE_VOCABULARY = "Vocabulary";
    public static String TABLE_NOTES = "Notes";
    public static String TABLE_CONVERSATION = "Conversation";
    public static String TABLE_TOPIC = "Topic";
    public static String TABLE_TOPIC_TITLE = "TopicTitle";

    public static final String COL_CHUNGID = "ChungID";

    Activity activity;

    public DataHelper(Activity activity) {
        this.activity = activity;
    }

    public synchronized JSONArray convertDatabaseToJson(String databaseName, String table) {
        String myPath = activity.getExternalCacheDir().toString() + "/" + databaseName;
        String myTable = table;//Set name of your table
        SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        String searchQuery = "SELECT  * FROM " + myTable;
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
                        Log.d("TAG", e.getMessage());
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
}
