package com.limerobotsoftware.ohiodevfest.ui.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.limerobotsoftware.ohiodevfest.R;
import com.limerobotsoftware.ohiodevfest.model.Session;
import com.limerobotsoftware.ohiodevfest.model.Social;
import com.limerobotsoftware.ohiodevfest.model.Speaker;
import com.limerobotsoftware.ohiodevfest.ui.SpeakerActivity;

import org.parceler.Parcels;

/**
 * Created by andy on 10/31/16.
 */

public class SpeakerFragment extends BaseFragment {

    TextView company;
    TextView bio;
    TextView title;
    TextView description;
    ImageView twitter;
    ImageView gPlus;
    private Speaker speaker;
    private Session session;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_speaker, container, false);
        company = (TextView) view.findViewById(R.id.company);
        bio = (TextView) view.findViewById(R.id.bio);
        twitter = (ImageView) view.findViewById(R.id.twitter);
        gPlus = (ImageView) view.findViewById(R.id.g_plus);
        title = (TextView) view.findViewById(R.id.title);
        description = (TextView) view.findViewById(R.id.description);

        Bundle args = getArguments();
        if (args != null) {
            speaker = Parcels.unwrap(args.getParcelable(SpeakerActivity.SPEAKER_KEY));
            session = Parcels.unwrap(args.getParcelable(SpeakerActivity.SESSION_KEY));
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        company.setText(speaker.getCompany());
        bio.setText(speaker.getBio());
        title.setText(session.getTitle());
        description.setText(session.getDescription());
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
    }
}
