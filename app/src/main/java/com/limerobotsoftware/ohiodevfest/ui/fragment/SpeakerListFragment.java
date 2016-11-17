package com.limerobotsoftware.ohiodevfest.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.limerobotsoftware.ohiodevfest.OhioDevFestApplication;
import com.limerobotsoftware.ohiodevfest.R;
import com.limerobotsoftware.ohiodevfest.model.Speaker;
import com.limerobotsoftware.ohiodevfest.ui.adapter.SpeakerAdapter;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

/**
 * Created by andy on 10/30/16.
 */

public class SpeakerListFragment extends BaseFragment{

    private RecyclerView recyclerView;
    private SpeakerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_speaker_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.speakers);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        messenger.refreshFragment(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.speakerListSpanCount)));
        recyclerView.setNestedScrollingEnabled(false);
        if (recyclerView.getAdapter() == null && adapter != null)
            recyclerView.setAdapter(adapter);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = OhioDevFestApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
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
