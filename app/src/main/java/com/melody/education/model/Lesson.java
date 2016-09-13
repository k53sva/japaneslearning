package com.melody.education.model;


/**
 * Created by K53SV on 8/29/2016.
 */
public class Lesson {
    public int id;
    public String LessonName;
    public String Anh;
    public String Nhat;
    public String Romaji;
    public String Detail;

    public Lesson(String anh, String detail) {
        Anh = anh;
        Detail = detail;
    }
}
