package com.melody.education.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by K53SV on 11/14/2016.
 */

public class QuizGridView extends GridView {
    private int mRequestedNumColumns = 4;

    public QuizGridView(Context context) {
        super(context);
    }

    public QuizGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QuizGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setNumColumns(int numColumns) {
        super.setNumColumns(numColumns);

        if (numColumns != mRequestedNumColumns) {
            mRequestedNumColumns = numColumns;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mRequestedNumColumns > 0) {
            int width = (mRequestedNumColumns * getColumnWidth())
                    + ((mRequestedNumColumns-1) * getHorizontalSpacing())
                    + getListPaddingLeft() + getListPaddingRight();

            setMeasuredDimension(width, getMeasuredHeight());
        }
    }
}
