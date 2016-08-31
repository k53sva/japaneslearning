package com.melody.education.net;

import com.melody.education.model.Lesson;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by K53SV on 8/29/2016.
 */
public interface ApiInterface {
    @GET("beginner/1/lessonlist.txt")
    Call<List<Lesson>> getBeginnerLessonList();
}
