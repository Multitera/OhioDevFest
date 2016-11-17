package com.limerobotsoftware.ohiodevfest.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.limerobotsoftware.ohiodevfest.OhioDevFestApplication;
import com.limerobotsoftware.ohiodevfest.R;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by andy on 11/3/16.
 */

public class PartnersFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_partners, container, false);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = OhioDevFestApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
