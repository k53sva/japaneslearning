package com.melody.education.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.melody.education.R;
import com.melody.education.adapter.SimpleListAdapter;
import com.melody.education.data.Samples;
import com.melody.education.ui.BaseFragment;
import com.melody.education.ui.LearningActivity;

/**
 * Created by K53SV on 8/29/2016.
 */
public class MainFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ListView exampleList = (ListView) v.findViewById(R.id.selection_activity_list);
        exampleList.setAdapter(new SimpleListAdapter(getActivity(), Samples.getAudioSamples()));
        exampleList.setOnItemClickListener(this);
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startAudioPlayerActivity(position);
    }

    private void startAudioPlayerActivity(int selectedIndex) {
        Intent intent = new Intent(getActivity(), LearningActivity.class);
        intent.putExtra(LessonFragment.EXTRA_INDEX, selectedIndex);
        startActivity(intent);
    }
}
