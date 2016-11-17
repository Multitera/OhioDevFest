package com.limerobotsoftware.ohiodevfest.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.limerobotsoftware.ohiodevfest.OhioDevFestApplication;
import com.limerobotsoftware.ohiodevfest.R;
import com.limerobotsoftware.ohiodevfest.model.Timeslot;
import com.limerobotsoftware.ohiodevfest.ui.adapter.TimeslotAdapter;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

/**
 * Created by andy on 11/8/16.
 */

public class ScheduleFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private TimeslotAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.timeslots);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        messenger.refreshFragment(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        if (recyclerView.getAdapter() == null && adapter != null)
            recyclerView.setAdapter(adapter);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = OhioDevFestApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    public void populateTimeslots(List<Timeslot> timeslots) {
        if(adapter == null) {
            adapter = new TimeslotAdapter();
            recyclerView.setAdapter(adapter);
        }
        adapter.setTimeslots(timeslots);
        adapter.notifyDataSetChanged();
        recyclerView.invalidate();
    }
}
