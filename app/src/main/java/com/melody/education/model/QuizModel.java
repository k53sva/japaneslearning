package com.melody.education.model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by K53SV on 11/12/2016.
 */

public class QuizModel {
    public static final int CHECKED = 1;
    public static final int RESET = 0;

    public List<ShortQuiz> items;
    public int status = 0;
    public HashMap<Integer, Boolean> isCheck;
}
