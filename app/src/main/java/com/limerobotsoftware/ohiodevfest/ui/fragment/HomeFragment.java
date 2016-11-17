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
import com.limerobotsoftware.ohiodevfest.ui.adapter.FeaturedSpeakerAdapter;
import com.limerobotsoftware.ohiodevfest.model.Speaker;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

/**
 * Created by andy on 10/26/16.
 */

public class HomeFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private FeaturedSpeakerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.featured_speakers);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        messenger.refreshFragment(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.featuredSpanCount)));
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
            adapter = new FeaturedSpeakerAdapter();
            recyclerView.setAdapter(adapter);
        }
        adapter.setFeaturedSpeakers(speakers);
        adapter.notifyDataSetChanged();
        recyclerView.invalidate();
    }
}
