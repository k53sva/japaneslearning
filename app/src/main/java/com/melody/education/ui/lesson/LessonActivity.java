package com.melody.education.ui.lesson;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.melody.education.R;
import com.melody.education.utils.Utils;

/**
 * Created by K53SV on 11/2/2016.
 */

public class LessonActivity extends AppCompatActivity {
    public static final String EXTRA_INDEX = "EXTRA_INDEX";
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_IMAGE = "EXTRA_IMAGE";
    private TabLayout tabLayout;
    private String ChungID;
    private String title;
    private String image;

    public static void launchActivity(Context context, String ChungId, String title, String image) {
        Intent intent = new Intent(context, LessonActivity.class);
        intent.putExtra(EXTRA_INDEX, ChungId);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_IMAGE, image);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null) {
            ChungID = getIntent().getExtras().getString(EXTRA_INDEX);
            title = getIntent().getExtras().getString(EXTRA_TITLE);
            image = getIntent().getExtras().getString(EXTRA_IMAGE);
        }
        setContentView(R.layout.activity_lesson);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        getData();
        DialogFragment fragment = DialogFragment.newInstance(ChungID, title, image);
        Utils.startFragment(LessonActivity.this, fragment);
    }

    private void getData() {
        tabLayout.addTab(tabLayout.newTab().setText("Dialog"));
        tabLayout.addTab(tabLayout.newTab().setText("Key Sentences"));
        tabLayout.addTab(tabLayout.newTab().setText("Vocabulary"));
        tabLayout.addTab(tabLayout.newTab().setText("Notes"));
        tabLayout.addTab(tabLayout.newTab().setText("ShortQuiz"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        DialogFragment fragment = DialogFragment.newInstance(ChungID, title, image);
                        Utils.startFragment(LessonActivity.this, fragment);
                        break;
                    case 1:
                        KeySentencesFragment key = KeySentencesFragment.newInstance(ChungID);
                        Utils.startFragment(LessonActivity.this, key);
                        break;
                    case 2:
                        LessonVocabularyFragment voca = LessonVocabularyFragment.newInstance(ChungID);
                        Utils.startFragment(LessonActivity.this, voca);
                        break;
                    case 3:
                        NotesFragment note = NotesFragment.newInstance(ChungID);
                        Utils.startFragment(LessonActivity.this, note);

                    case 4:
                        ShortQuizFragment quiz = new ShortQuizFragment();
                        Utils.startFragment(LessonActivity.this, quiz);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
