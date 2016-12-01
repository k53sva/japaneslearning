package com.melody.education.ui;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.melody.education.App;
import com.melody.education.R;
import com.melody.education.adapter.ConversationAdapter;
import com.melody.education.model.Version;
import com.melody.education.ui.conversation.ConversationActivity;
import com.melody.education.ui.conversation.ConversationFragment;
import com.melody.education.model.Conversation;
import com.melody.education.net.FetchData;
import com.melody.education.ui.kanji.KanjiActivity;
import com.melody.education.utils.DataCache;
import com.melody.education.utils.DataHelper;
import com.melody.education.utils.Utils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MaterialSearchView searchView;
    private Toolbar mToolbar;
    public AppBarLayout appBarLayout;
    public TextView tvTitle, tvDes;
    public ProgressDialog dialog;
    private Version version;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        version = DataCache.getInstance().pop(Version.class);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDes = (TextView) findViewById(R.id.tv_des);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setSupportActionBar(mToolbar);
        setupSearch();
        initNavigationView();
        Utils.startFragment(this, new ConversationListFragment());
        handleIntent(getIntent());
    }

    private void setupSearch() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                for (int i = 0; i < ConversationAdapter.conversationList.size(); i++) {
                    Conversation c = ConversationAdapter.conversationList.get(i);
                    if (c.Title.equals(query)) {
                        startLearningActivity(i);
                        return false;
                    }
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });

        App.getDataHelper().getData(DataHelper.DATABASE_CONVERSATION, DataHelper.TABLE_CONVERSATION, Conversation[].class)
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .filter(m -> m.Picture != null)
                .filter(m -> m.Picture.length() > 0)
                .map(this::fillAudio)
                .toList()
                .doOnNext(m -> ConversationAdapter.conversationList = m)
                .flatMap(Observable::from)
                .map(m -> m.Title)
                .toList()
                .map(m -> m.toArray(new String[m.size()]))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(m -> searchView.setSuggestions(m), throwable -> Log.e(TAG, throwable.toString()));
    }

    private void initNavigationView() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_setting:
                startActivity(new Intent(this, UserSettingActivity.class));
                break;

            case R.id.nav_feedback:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "abc@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                break;

            case R.id.nav_beginner_conversation:
                Utils.startFragment(MainActivity.this, new ConversationListFragment());
                break;

            case R.id.menu_syllabaries:
                Utils.startFragment(this, new SyllabariesFragment());
                break;

            case R.id.nav_menu_lessons:
                checkVersionLesson();
                break;

            case R.id.nav_menu_topics:
                checkVersionTopic();
                break;

            case R.id.nav_menu_kanji:
                checkVersionKanji();
                break;

            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void checkVersionKanji() {
        float ver = preferences.getFloat(Version.KANJI_KEY, 0);

        if (version.kanji == ver) {
            if (Utils.checkFileExits(String.format("%s/%s", this.getExternalCacheDir(), DataHelper.DATABASE_KANJI)))
                startActivity(new Intent(this, KanjiActivity.class));
            else
                getDatKanji(version.kanji);
        } else
            getDatKanji(version.kanji);

    }

    private void getDatKanji(float v) {
        dialog.show();
        new FetchData(this).getDataKanji()
                .doOnNext(m -> dialog.dismiss())
                .subscribe(m -> {
                    if (m) {
                        editor.putFloat(Version.KANJI_KEY, v);
                        editor.commit();
                        startActivity(new Intent(this, KanjiActivity.class));
                    } else
                        showAlertAction(this, id -> startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS))
                                , "No connection. Please check connect internet");
                });
    }

    private void checkVersionTopic() {
        float ver = preferences.getFloat(Version.TOPICS_KEY, 0);

        if (version.topics == ver)
            if (Utils.checkFileExits(String.format("%s/%s", this.getExternalCacheDir(), DataHelper.DATABASE_TOPICS)))
                Utils.startFragment(this, new TopicListFragment());
            else
                getDatTopics(version.topics);
        else
            getDatTopics(version.topics);
    }

    private void getDatTopics(float v) {
        dialog.show();
        new FetchData(this).getDataTopic()
                .doOnNext(m -> dialog.dismiss())
                .subscribe(m -> {
                    if (m) {
                        Utils.startFragment(this, new TopicListFragment());
                        editor.putFloat(Version.TOPICS_KEY, v);
                        editor.commit();
                    } else
                        showAlertAction(this, id -> startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS))
                                , "No connection. Please check connect internet");
                });
    }

    private void checkVersionLesson() {
        float ver = preferences.getFloat(Version.LESSON_KEY, 0);

        if (version.Lesson == ver)
            if (Utils.checkFileExits(String.format("%s/%s", this.getExternalCacheDir(), DataHelper.DATABASE_LESSON)))
                Utils.startFragment(this, new LessonListFragment());
            else
                getDataLesson(version.Lesson);
        else
            getDataLesson(version.Lesson);

    }

    private void getDataLesson(float v) {
        dialog.show();
        new FetchData(this).getDataLesson()
                .doOnNext(m -> dialog.dismiss())
                .subscribe(m -> {
                    if (m) {
                        Utils.startFragment(this, new LessonListFragment());
                        editor.putFloat(Version.LESSON_KEY, v);
                        editor.commit();
                    } else
                        showAlertAction(this, id -> startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS))
                                , "No connection. Please check connect internet");
                });
    }

    private void startLearningActivity(int selectedIndex) {
        Intent intent = new Intent(this, ConversationActivity.class);
        intent.putExtra(ConversationFragment.EXTRA_INDEX, selectedIndex);
        startActivity(intent);
    }

    private Conversation fillAudio(Conversation c) {
        c.Audio = String.format("%s%s", FetchData.ROOT_URL, c.Audio);
        return c;
    }
}
