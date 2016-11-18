package com.melody.education.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by K53SV on 8/31/2016.
 */
public class DataHelper {
    private static final String TAG = DataHelper.class.getSimpleName();
    public static final String DATABASE_CONVERSATION = "conversation.sqlite";
    public static final String DATABASE_TOPICS = "topics.sqlite";
    public static final String DATABASE_KANJI = "kanji.sqlite";
    public static final String DATABASE_LESSON = "Lesson.sqlite";

    public static final String TABLE_VOCABULARY = "Vocabulary";
    public static final String TABLE_NOTES = "Notes";
    public static final String TABLE_CONVERSATION = "Conversation";
    public static final String TABLE_TOPIC = "Topic";
    public static final String TABLE_TOPIC_TITLE = "TopicTitle";
    public static final String TABLE_EXAMPLES = "Examples";
    public static final String TABLE_KANJI_CONTENT = "KanjiContent";
    public static final String TABLE_KANJI_GROUP = "KanjiGroup";
    public static final String TABLE_REFERENCE = "Reference";
    public static final String TABLE_WORK_KUN_READING = "WordKunReading";
    public static final String TABLE_WORK_ON_READING = "WordOnReading";
    public static final String TABLE_LESSON_TITLE = "LessonTitle";
    public static final String TABLE_DIALOGUE_1 = "Dialogue1";
    public static final String TABLE_DIALOGUE_2 = "Dialogue2";
    public static final String TABLE_SHORT_QUIZ = "ShortQuiz1";
    public static final String TABLE_KEY_SENTENCES = "KeySentences";
    public static final String TABLE_ANSWER_QUIZ = "AnswerShortQuiz1";


    private Context activity;
    private Gson gson = new Gson();

    public DataHelper(Context activity) {
        this.activity = activity;
    }

    private synchronized JSONArray convertDatabaseToJson(String databaseName, String table) {
        JSONArray resultSet = new JSONArray();
        try {
            String myPath = String.format("%s/%s", activity.getExternalCacheDir(), databaseName);
            SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
            String searchQuery = "SELECT  * FROM " + table;
            Cursor cursor = myDataBase.rawQuery(searchQuery, null);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public synchronized <T> Observable<T> getData(String database, String table, Class<T> clazz) {
        return Observable.just(convertDatabaseToJson(database, table))
                .subscribeOn(Schedulers.io())
                .map(m -> gson.fromJson(m.toString(), clazz));
    }
}
