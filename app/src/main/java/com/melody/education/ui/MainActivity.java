package com.melody.education.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.melody.education.R;
import com.melody.education.adapter.ConversationAdapter;
import com.melody.education.ui.fragment.ConversationFragment;
import com.melody.education.model.Conversation;
import com.melody.education.net.FetchData;
import com.melody.education.ui.kanji.KanjiActivity;
import com.melody.education.utils.DataHelper;
import com.melody.education.utils.Utils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MaterialSearchView searchView;
    private Toolbar mToolbar;
    public AppBarLayout appBarLayout;
    public TextView tvTitle, tvDes;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setSupportActionBar(mToolbar);

        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDes = (TextView) findViewById(R.id.tv_des);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
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

        DataHelper helper = new DataHelper(this);
        helper.getData(DataHelper.DATABASE_CONVERSATION, DataHelper.TABLE_CONVERSATION, Conversation[].class)
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
                Utils.startFragment(this, new LessonListFragment());
                break;

            case R.id.nav_menu_topics:
                Utils.startFragment(this, new TopicListFragment());
                break;

            case R.id.nav_menu_kanji:
                startActivity(new Intent(this, KanjiActivity.class));
                break;

            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
