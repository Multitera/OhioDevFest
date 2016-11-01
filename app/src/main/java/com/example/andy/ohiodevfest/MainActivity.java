package com.example.andy.ohiodevfest;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.andy.ohiodevfest.model.Session;
import com.example.andy.ohiodevfest.model.Speaker;
import com.example.andy.ohiodevfest.ui.fragment.HomeFragment;
import com.example.andy.ohiodevfest.ui.fragment.SpeakerFragment;
import com.example.andy.ohiodevfest.ui.fragment.SpeakerListFragment;
import com.example.andy.ohiodevfest.utils.Presenter;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Presenter presenter = new Presenter(this, Model.getInstance());
    private enum FragmentTags {HOME, SCHEDULE, SPEAKERS, PARTNERS, CONDUCT, SPEAKER, SESSION}
    private FragmentTags currentFragment;
    private int speakerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment(), FragmentTags.HOME.toString())
                    .commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        if (id == R.id.nav_home) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment(), FragmentTags.HOME.toString())
                    .commit();
        } else if (id == R.id.nav_schedule) {

        } else if (id == R.id.nav_speakers) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new SpeakerListFragment(), FragmentTags.SPEAKERS.toString())
                    .commit();
        } else if (id == R.id.nav_partners) {

        } else if (id == R.id.nav_conduct) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        FragmentTags fragmentTag = FragmentTags.valueOf(fragment.getTag());

        //#enumsMatter
        switch (fragmentTag) {
            case HOME:
                currentFragment = fragmentTag;
                presenter.getSpeakers(true, null);
                break;
            case SPEAKERS:
                currentFragment = fragmentTag;
                presenter.getSpeakers(true, null);
                break;
            case SPEAKER:
                currentFragment = fragmentTag;
                presenter.getSpeakers(true, null);
                break;
        }
    }

    public void pushSpeakers (List<Speaker> speakers) {
        switch (currentFragment) {
            case HOME:
                ((HomeFragment) getSupportFragmentManager().findFragmentByTag(currentFragment.toString())).populateSpeakers(speakers);
                break;
            case SPEAKERS:
                ((SpeakerListFragment) getSupportFragmentManager().findFragmentByTag(currentFragment.toString())).populateSpeakers(speakers);
                break;
            case SPEAKER:
                SpeakerFragment speakerFragment = (SpeakerFragment) getSupportFragmentManager().findFragmentByTag(currentFragment.toString());
                speakerFragment.setSpeaker(speakers.get(0));
                break;
        }
    }

    public void pushSessions (List<Session> sessions) {
        switch (currentFragment) {
            case SPEAKER:
                ((SpeakerFragment) getSupportFragmentManager().findFragmentByTag(currentFragment.toString())).populateSessions(sessions);
                break;
        }
    }

    public void openSpeaker(View view) {
        speakerId = (int) view.getTag();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new SpeakerFragment(), FragmentTags.SPEAKER.toString())
                .commit();
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
