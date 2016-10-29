package com.example.andy.ohiodevfest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andy.ohiodevfest.adapter.FeaturedSpeakerAdapter;
import com.example.andy.ohiodevfest.model.Speaker;

import java.util.List;

/**
 * Created by andy on 10/26/16.
 */

public class HomeFragment extends android.support.v4.app.Fragment {

    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayout;
    private FeaturedSpeakerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mLayout = new GridLayoutManager(getContext(), getResources().getInteger(R.integer.spanCount));
        mRecyclerView = (RecyclerView) view.findViewById(R.id.featured_speakers);
        mRecyclerView.setLayoutManager(mLayout);

        return view;
    }

    public void populateSpeakers(List<Speaker> speakers) {
        if(mAdapter == null) {
            mAdapter = new FeaturedSpeakerAdapter();
            mRecyclerView.setAdapter(mAdapter);
        }
        mAdapter.setFeaturedSpeakers(speakers);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.invalidate();
    }
}
