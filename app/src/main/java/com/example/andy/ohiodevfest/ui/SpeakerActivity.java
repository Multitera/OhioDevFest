package com.example.andy.ohiodevfest.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.andy.ohiodevfest.R;
import com.example.andy.ohiodevfest.model.Speaker;
import com.example.andy.ohiodevfest.ui.fragment.SpeakerFragment;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

/**
 * Created by andy on 11/2/16.
 */

public class SpeakerActivity extends AppCompatActivity {

    private final String SPEAKER_KEY = "speaker";
    private Speaker speaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);

        speaker = Parcels.unwrap(getIntent().getParcelableExtra(SPEAKER_KEY));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(speaker.getName());

        Picasso.with(this)
                .load("https://ohiodevfest.com"+speaker.getPhotoUrl())
                .into((ImageView) this.findViewById(R.id.photo));

        setSupportActionBar(toolbar);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new SpeakerFragment(), SPEAKER_KEY)
                .commit();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment.getTag().equals(SPEAKER_KEY)) {
            SpeakerFragment speakerFragment = (SpeakerFragment) fragment;
            speakerFragment.setSpeaker(speaker);
        }
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
