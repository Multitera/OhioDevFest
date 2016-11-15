package com.limerobotsoftware.ohiodevfest.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.limerobotsoftware.ohiodevfest.R;
import com.limerobotsoftware.ohiodevfest.model.Speaker;
import com.limerobotsoftware.ohiodevfest.ui.fragment.SpeakerFragment;
import com.squareup.picasso.Picasso;

import static org.parceler.Parcels.unwrap;

/**
 * Created by andy on 11/2/16.
 */

public class SpeakerActivity extends AppCompatActivity {

    public final static String SPEAKER_KEY = "speaker";
    public final static String SESSION_KEY = "session";
    private final static String SPEAKER_FRAGMENT_TAG = "SpeakerFragmentTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);

        Speaker speaker = unwrap(getIntent().getParcelableExtra(SPEAKER_KEY));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(speaker.getName());

        Picasso.with(this)
                .load("https://ohiodevfest.com"+ speaker.getPhotoUrl())
                .into((ImageView) this.findViewById(R.id.photo));

        setSupportActionBar(toolbar);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, getSpeakerFragment(), SPEAKER_FRAGMENT_TAG)
                .commit();
    }

    private SpeakerFragment getSpeakerFragment() {
        SpeakerFragment fragment = (SpeakerFragment) getSupportFragmentManager().findFragmentByTag(SPEAKER_FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new SpeakerFragment();
            Bundle args = new Bundle();
            args.putParcelable(SESSION_KEY, getIntent().getParcelableExtra(SESSION_KEY));
            args.putParcelable(SPEAKER_KEY, getIntent().getParcelableExtra(SPEAKER_KEY));
            fragment.setArguments(args);
        }

        return fragment;
    }

    public void openGPlus(View view) {
        String profile = (String) view.getTag();
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setClassName("com.google.android.apps.plus",
                    "com.google.android.apps.plus.phone.UrlGatewayActivity");
            intent.putExtra("customAppUri", profile);
            startActivity(intent);
        } catch(ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/"+profile)));
        }
    }

    public void openTwitter(View view) {
        String twitterName = (String) view.getTag();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("twitter://user?screen_name=" + twitterName)));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://twitter.com/#!/" + twitterName)));
        }
    }
}
