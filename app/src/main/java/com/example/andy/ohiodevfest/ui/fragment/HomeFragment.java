package com.example.andy.ohiodevfest.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andy.ohiodevfest.R;
import com.example.andy.ohiodevfest.ui.adapter.FeaturedSpeakerAdapter;
import com.example.andy.ohiodevfest.model.Speaker;

import java.util.List;

/**
 * Created by andy on 10/26/16.
 */

public class HomeFragment extends android.support.v4.app.Fragment {

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

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.featuredSpanCount)));
        recyclerView.setNestedScrollingEnabled(false);
        if (recyclerView.getAdapter() == null && adapter != null)
            recyclerView.setAdapter(adapter);
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
