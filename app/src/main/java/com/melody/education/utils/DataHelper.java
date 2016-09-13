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
    public static String TABLE_LESSON = "Lesson";
    public static String TABLE_VOCABULARY = "Vocabulary";
    public static String TABLE_NOTES = "Notes";
    public static String TABLE_CONVERSATION = "Conversation";

    public static final String COL_CHUNGID = "ChungID";

    Activity activity;

    public DataHelper(Activity activity) {
        this.activity = activity;
    }

    /**
     * Convert DataBase to Json
     */
    public synchronized JSONArray convertDatabaseToJson(String databaseName, String table) {
        String myPath = activity.getExternalCacheDir().toString() + "/" + databaseName;// Set path to your database
        Log.e(TAG, myPath);
        String myTable = table;//Set name of your table

        //or you can use `context.getDatabasePath("my_db_test.db")`

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
        Log.d("TAG", resultSet.toString());
        myDataBase.close();
        return resultSet;
    }


    /**
     * Convert DataBase to Json
     */
    public synchronized JSONArray convertDatabaseToJson(String databaseName, String table, String clause) {
        String myPath = activity.getExternalCacheDir().toString() + "/" + databaseName;// Set path to your database
        String myTable = table;//Set name of your table
        Log.e(TAG, myPath);

        //or you can use `context.getDatabasePath("my_db_test.db")`

        SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        String searchQuery = "SELECT  * FROM " + myTable + " WHERE " + clause;
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
        Log.d("TAG", resultSet.toString());
        myDataBase.close();
        return resultSet;
    }

    public synchronized JSONArray convertDatabaseToJsonLike(String databaseName, String table, String clause) {
        String myPath = activity.getExternalCacheDir().toString() + "/" + databaseName;// Set path to your database
        String myTable = table;//Set name of your table
        Log.e(TAG, myPath);

        //or you can use `context.getDatabasePath("my_db_test.db")`

        SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        String searchQuery = "SELECT  * FROM " + myTable + " " + clause;
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
        Log.d("TAG", resultSet.toString());
        myDataBase.close();
        return resultSet;
    }
}
