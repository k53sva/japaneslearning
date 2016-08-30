package com.melody.education.ui;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melody.education.R;
import com.melody.education.adapter.LessonAdapter;
import com.melody.education.model.Album;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by K53SV on 8/29/2016.
 */
public class HomeFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private LessonAdapter adapter;
    private List<Album> albumList;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_main, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);

        albumList = new ArrayList<>();
        adapter = new LessonAdapter(getActivity(), albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(0), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepareAlbums();
        mSwipeRefreshLayout.setOnRefreshListener(this::refreshItems);
        return v;
    }

    /**
     * Refresh list
     */
    void refreshItems() {
        // Load items
        // ...
        // Load complete
        new Handler().postDelayed(this::onItemsLoadComplete, 5000);
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...
        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
                R.drawable.album9,
                R.drawable.album10,
                R.drawable.album11};

        Album a = new Album("レッスンのナンバーワン", "こんにちはどのようにあなたがやっている今日？私は細かいおかげであなたをよ", covers[0]);
        albumList.add(a);

        a = new Album("レッスンのナンバーワン", "こんにちはどのようにあなたがやっている今日？私は細かいおかげであなたをよ", covers[1]);
        albumList.add(a);

        a = new Album("レッスンのナンバーワン 5", "こんにちはどのようにあなたがやっている今日？私は細かいおかげであなたをよ", covers[2]);
        albumList.add(a);

        a = new Album("レッスンのナンバーワン", "こんにちはどのようにあなたがやっている今日？私は細かいおかげであなたをよ", covers[3]);
        albumList.add(a);

        a = new Album("レッスンのナンバーワン", "こんにちはどのようにあなたがやっている今日？私は細かいおかげであなたをよ", covers[4]);
        albumList.add(a);

        a = new Album("レッスンのナンバーワン", "こんにちはどのようにあなたがやっている今日？私は細かいおかげであなたをよ", covers[5]);
        albumList.add(a);

        a = new Album("レッスンのナンバーワン", "こんにちはどのようにあなたがやっている今日？私は細かいおかげであなたをよ", covers[6]);
        albumList.add(a);

        a = new Album("レッスンのナンバーワン", "こんにちはどのようにあなたがやっている今日？私は細かいおかげであなたをよ", covers[7]);
        albumList.add(a);

        a = new Album("レッスンのナンバーワン", "こんにちはどのようにあなたがやっている今日？私は細かいおかげであなたをよ", covers[8]);
        albumList.add(a);

        a = new Album("レッスンのナンバーワン", "こんにちはどのようにあなたがやっている今日？私は細かいおかげであなたをよ", covers[9]);
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
