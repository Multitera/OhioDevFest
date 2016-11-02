package com.example.andy.ohiodevfest.ui.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andy.ohiodevfest.R;
import com.example.andy.ohiodevfest.model.Session;
import com.example.andy.ohiodevfest.model.Social;
import com.example.andy.ohiodevfest.model.Speaker;
import com.example.andy.ohiodevfest.ui.adapter.SessionViewAdapter;

import java.util.List;

/**
 * Created by andy on 10/31/16.
 */

public class SpeakerFragment extends android.support.v4.app.Fragment {

    TextView company;
    TextView bio;
    ImageView twitter;
    ImageView gPlus;
    private RecyclerView recyclerView;
    private SessionViewAdapter adapter;
    private Speaker speaker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_speaker, container, false);
        company = (TextView) view.findViewById(R.id.company);
        bio = (TextView) view.findViewById(R.id.bio);
        twitter = (ImageView) view.findViewById(R.id.twitter);
        gPlus = (ImageView) view.findViewById(R.id.g_plus);
        recyclerView = (RecyclerView) view.findViewById(R.id.sessions);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        company.setText(speaker.getCompany());
        bio.setText(speaker.getBio());
        Resources resources = getContext().getResources();

        for (Social social : speaker.getSocials()) {
            if (social.getName().equals("Twitter")) {
                twitter.setTag(social.getLink().substring(resources.getInteger(R.integer.twitterStart), social.getLink().length()-1));
                twitter.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_twitter_box, null));
            } else if (social.getName().equals("Google+")) {
                gPlus.setTag(social.getLink().substring(resources.getInteger(R.integer.gPlusStart)));
                gPlus.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_google_plus_box, null));
            }
        }

        recyclerView.setNestedScrollingEnabled(false);
        if (recyclerView.getAdapter() == null && adapter != null)
            recyclerView.setAdapter(adapter);
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public void populateSessions(List<Session> sessions) {
        if(adapter == null) {
            adapter = new SessionViewAdapter();
            recyclerView.setAdapter(adapter);
        }
        adapter.setSessions(sessions);
        adapter.notifyDataSetChanged();
        recyclerView.invalidate();
    }
}