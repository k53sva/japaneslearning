package com.melody.education.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.melody.education.R;
import com.melody.education.adapter.ConversationAdapter;
import com.melody.education.adapter.NoteAdapter;
import com.melody.education.adapter.VocabularyAdapter;
import com.melody.education.model.Note;
import com.melody.education.model.Vocabulary;
import com.melody.education.utils.DataHelper;
import com.melody.education.utils.GridSpacingItemDecoration;
import com.melody.education.utils.Utils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by K53SV on 8/29/2016.
 */
public class NotesFragment extends Fragment {
    public static final String EXTRA_INDEX = "EXTRA_INDEX";
    RecyclerView recyclerView;
    NoteAdapter adapter;
    List<Note> noteList = new ArrayList<>();
    private int selectedIndex = 0;

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vocabulary, container, false);
        initView(v);
        retrieveExtras();
        getData();
        return v;
    }

    private void initView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_vocabulary);
        adapter = new NoteAdapter(getActivity(), noteList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(10, Utils.dpToPx(getActivity(), 1), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    private void getData() {
        Gson gson = new Gson();
        DataHelper helper = new DataHelper(getActivity());
        JSONArray array =
                helper.convertDatabaseToJson(DataHelper.DATABASE_CONVERSATION, DataHelper.TABLE_NOTES,
                        DataHelper.COL_CHUNGID
                                + " ='"
                                + ConversationAdapter.conversationList.get(selectedIndex).ChungID
                                + "'");
        try {
            for (int i = 0; i < array.length(); i++) {
                noteList.add(gson.fromJson(array.getString(i), Note.class));
            }
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
