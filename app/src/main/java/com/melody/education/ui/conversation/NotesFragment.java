package com.melody.education.ui.conversation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melody.education.App;
import com.melody.education.R;
import com.melody.education.adapter.ConversationAdapter;
import com.melody.education.adapter.NoteAdapter;
import com.melody.education.model.Note;
import com.melody.education.utils.DataHelper;
import com.melody.education.utils.GridSpacingItemDecoration;
import com.melody.education.utils.Utils;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by K53SV on 8/29/2016.
 */
public class NotesFragment extends Fragment {
    private static final String TAG = NotesFragment.class.getSimpleName();
    public static final String EXTRA_INDEX = "EXTRA_INDEX";
    private int selectedIndex = 0;
    private NoteAdapter adapter;

    public static NotesFragment newInstance(int index) {
        NotesFragment fragment = new NotesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void retrieveExtras() {
        if (getArguments() != null) {
            selectedIndex = getArguments().getInt(EXTRA_INDEX, 0);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vocabulary, container, false);
        initView(v);
        retrieveExtras();
        getData();
        return v;
    }

    private void initView(View v) {
        adapter = new NoteAdapter(getActivity(), new ArrayList<>());
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_vocabulary);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(10, Utils.dpToPx(getActivity(), 1), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        App.getDataHelper().getData(DataHelper.DATABASE_CONVERSATION, DataHelper.TABLE_NOTES, Note[].class)
                .flatMap(Observable::from)
                .filter(m -> m.ChungID != null)
                .filter(m -> m.ChungID.equals(ConversationAdapter.conversationList.get(selectedIndex).ChungID))
                .toList()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(m -> adapter.setModel(m), Throwable::fillInStackTrace);
    }
}
