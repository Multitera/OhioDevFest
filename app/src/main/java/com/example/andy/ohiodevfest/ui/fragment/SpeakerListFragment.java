package com.example.andy.ohiodevfest.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andy.ohiodevfest.R;
import com.example.andy.ohiodevfest.ui.adapter.SpeakerAdapter;
import com.example.andy.ohiodevfest.model.Speaker;

import java.util.List;

/**
 * Created by andy on 10/30/16.
 */

public class SpeakerListFragment extends android.support.v4.app.Fragment {

    private RecyclerView recyclerView;
    private SpeakerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_speaker_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.speakers);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.speakerListSpanCount)));
        recyclerView.setNestedScrollingEnabled(false);

        return view;
    }

    public void populateSpeakers(List<Speaker> speakers) {
        if(adapter == null) {
            adapter = new SpeakerAdapter();
            recyclerView.setAdapter(adapter);
        }
        adapter.setSpeakers(speakers);
        adapter.notifyDataSetChanged();
        recyclerView.invalidate();
    }
}
