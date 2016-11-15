package com.limerobotsoftware.ohiodevfest.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.limerobotsoftware.ohiodevfest.Model;
import com.limerobotsoftware.ohiodevfest.R;
import com.limerobotsoftware.ohiodevfest.model.Schedule;
import com.limerobotsoftware.ohiodevfest.model.Session;
import com.limerobotsoftware.ohiodevfest.model.Speaker;
import com.limerobotsoftware.ohiodevfest.model.Timeslot;
import com.limerobotsoftware.ohiodevfest.ui.fragment.ConductFragment;
import com.limerobotsoftware.ohiodevfest.ui.fragment.HomeFragment;
import com.limerobotsoftware.ohiodevfest.ui.fragment.PartnersFragment;
import com.limerobotsoftware.ohiodevfest.ui.fragment.ScheduleFragment;
import com.limerobotsoftware.ohiodevfest.ui.fragment.SpeakerListFragment;

import org.parceler.Parcels;

import java.util.List;

import io.realm.RealmList;

import static com.limerobotsoftware.ohiodevfest.ui.MainActivity.FragmentTags.HOME;
import static com.limerobotsoftware.ohiodevfest.ui.MainActivity.FragmentTags.SCHEDULE;
import static com.limerobotsoftware.ohiodevfest.ui.MainActivity.FragmentTags.SPEAKERS;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    com.limerobotsoftware.ohiodevfest.ui.Presenter presenter = new com.limerobotsoftware.ohiodevfest.ui.Presenter(this, Model.getInstance());
    SwipeRefreshLayout swipeRefreshLayout;
    private CoordinatorLayout coordinatorLayout;
    private FirebaseAnalytics firebaseAnalytics;
    private final String SPEAKER_KEY = "speaker";
    private final String SESSION_KEY = "session";
    enum FragmentTags {HOME, SCHEDULE, SPEAKERS, PARTNERS, CONDUCT}
    private FragmentTags currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
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

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.refreshData();
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, getFragment(HOME.toString()), HOME.toString())
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
            String tag = HOME.toString();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, getFragment(tag), tag)
                    .commit();
        } else if (id == R.id.nav_schedule) {
            String tag = SCHEDULE.toString();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, getFragment(tag), tag)
                    .commit();

        } else if (id == R.id.nav_speakers) {
            String tag = SPEAKERS.toString();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, getFragment(tag), tag)
                    .commit();
        } else if (id == R.id.nav_partners) {
            String tag = FragmentTags.PARTNERS.toString();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, getFragment(tag), tag)
                    .commit();

        } else if (id == R.id.nav_conduct) {
            String tag = FragmentTags.CONDUCT.toString();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, getFragment(tag), tag)
                    .commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Fragment getFragment(String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {

            //#enumsMatter
            switch (FragmentTags.valueOf(tag)) {
                case HOME:
                    fragment = new HomeFragment();
                    break;
                case SCHEDULE:
                    fragment = new ScheduleFragment();
                    break;
                case SPEAKERS:
                    fragment = new SpeakerListFragment();
                    break;
                case PARTNERS:
                    fragment = new PartnersFragment();
                    break;
                case CONDUCT:
                    fragment = new ConductFragment();
                    break;
            }
        }

        return fragment;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        refreshFragment(fragment);
    }

    private void refreshFragment(Fragment fragment) {
        FragmentTags fragmentTag = FragmentTags.valueOf(fragment.getTag());
        currentFragment = fragmentTag;

        //#enumsMatter
        switch (fragmentTag) {
            case HOME:
                presenter.getSpeakers(true, null);
                break;
            case SPEAKERS:
                presenter.getSpeakers(true, null);
                break;
            case SCHEDULE:
                presenter.getSchedule("2016-11-19");
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
        }
    }

    public void pushSchedule (List<Schedule> schedules) {
        switch (currentFragment) {
            case SCHEDULE:
                List<Timeslot> timeslots = schedules.get(0).getTimeslots();
                ((ScheduleFragment) getSupportFragmentManager().findFragmentByTag(currentFragment.toString())).populateTimeslots(timeslots);
                break;
        }
    }

    public void refreshFinished() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public void retrofitFailed() {
        refreshFinished();
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Failed to load data", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.refreshData();
                    }
                });

        snackbar.show();
    }

    public void attendingChange (View view) {
        Session session = (Session) view.getTag();
        Bundle payload = new Bundle();

        //session.getAttending hasn't changed yet so report opposite.
        if (session.getAttending() != null && session.getAttending())
            payload.putString(FirebaseAnalytics.Param.VALUE, "stopped attending");
        else
            payload.putString(FirebaseAnalytics.Param.VALUE, "is attending");

        firebaseAnalytics.logEvent("attendingChange", payload);
        presenter.updateAttending(session.getId());
    }

    public void openSpeaker(View view) {
        Intent intent = new Intent(this, com.limerobotsoftware.ohiodevfest.ui.SpeakerActivity.class);
        Speaker speaker = (Speaker) view.getTag();
        Parcelable parcelableSpeaker = Parcels.wrap(speaker);
        intent.putExtra(SPEAKER_KEY, parcelableSpeaker);
        RealmList<Session> sessionList = speaker.getSessionList();
        Parcelable parcelableSession = Parcels.wrap(sessionList.get(0));
        intent.putExtra(SESSION_KEY, parcelableSession);
        TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(intent)
                .startActivities();
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

    public void openWebpage(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse((String) view.getTag())));
    }

    public void beExcellent(View view) {
        MediaPlayer.create(getApplicationContext(), R.raw.be_excellent).start();
    }
}
