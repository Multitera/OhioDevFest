package com.limerobotsoftware.ohiodevfest.ui.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.limerobotsoftware.ohiodevfest.OhioDevFestApplication;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by andy on 11/16/16.
 */

public class BaseFragment extends Fragment{
    FragmentMessenger messenger;

    public interface FragmentMessenger {
        void refreshFragment(Fragment fragment);
    }

    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            messenger = (FragmentMessenger) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " does not implement FragmentMessenger");
        }
    }

    @Override public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = OhioDevFestApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
