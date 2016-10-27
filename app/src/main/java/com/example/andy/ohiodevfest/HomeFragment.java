package com.example.andy.ohiodevfest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by andy on 10/26/16.
 */

public class HomeFragment extends android.support.v4.app.Fragment {

    private GridView gridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        gridView = (GridView) view.findViewById(R.id.featured_speakers);
        View emptyView = view.findViewById(R.id.empty_speakers);
        gridView.setEmptyView(emptyView);

        return view;
    }
}
