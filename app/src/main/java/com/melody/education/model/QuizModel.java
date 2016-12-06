package com.melody.education.model;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by K53SV on 11/12/2016.
 */

public class QuizModel {
    public static final int CHECKED = 1;
    public static final int RESET = 0;

    public List<Integer> stt = Stream.range(0, 50).map(x -> -1).collect(Collectors.toList());
    public List<ShortQuiz> items;
    public int status = 0;
    public HashMap<Integer, Boolean> isCheck;
}
