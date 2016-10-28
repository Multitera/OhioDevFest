package com.example.andy.ohiodevfest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.andy.ohiodevfest.adapter.FeaturedSpeakerAdapter;
import com.example.andy.ohiodevfest.model.Speaker;

import java.util.List;

/**
 * Created by andy on 10/26/16.
 */

public class HomeFragment extends android.support.v4.app.Fragment {

    private GridView mGridView;
    private FeaturedSpeakerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mGridView = (GridView) view.findViewById(R.id.featured_speakers);
        View emptyView = view.findViewById(R.id.empty_speakers);
        mGridView.setEmptyView(emptyView);

        return view;
    }

    public void populateSpeakers(List<Speaker> speakers) {
        if(mAdapter == null) {
            mAdapter = new FeaturedSpeakerAdapter(getContext());
            mGridView.setAdapter(mAdapter);
        }
        mAdapter.setData(speakers);
        mAdapter.notifyDataSetChanged();
        mGridView.invalidate();
    }
}
