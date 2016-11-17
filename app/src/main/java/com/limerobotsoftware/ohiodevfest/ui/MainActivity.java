package com.limerobotsoftware.ohiodevfest.ui;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
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
import com.limerobotsoftware.ohiodevfest.ui.fragment.BaseFragment;
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
        implements NavigationView.OnNavigationItemSelectedListener, BaseFragment.FragmentMessenger {

    com.limerobotsoftware.ohiodevfest.ui.Presenter presenter = new com.limerobotsoftware.ohiodevfest.ui.Presenter(this, Model.getInstance());
    SwipeRefreshLayout swipeRefreshLayout;
    private CoordinatorLayout coordinatorLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FirebaseAnalytics firebaseAnalytics;
    private static final String SPEAKER_KEY = "speaker";
    private static final String SESSION_KEY = "session";
    enum FragmentTags {HOME, SCHEDULE, SPEAKERS, PARTNERS, CONDUCT}

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

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Bundle payload = new Bundle();
            payload.putString(FirebaseAnalytics.Param.VALUE, "refresh swiped");
            firebaseAnalytics.logEvent("serviceEvents", payload);
            presenter.refreshData();
        });

        if (savedInstanceState == null) {
            navigationView.getMenu().getItem(0).setChecked(true);
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
        String tag = null;

        if (id == R.id.nav_home) {
            tag = HOME.toString();

        } else if (id == R.id.nav_schedule) {
            tag = SCHEDULE.toString();

        } else if (id == R.id.nav_speakers) {
            tag = SPEAKERS.toString();

        } else if (id == R.id.nav_partners) {
            tag = FragmentTags.PARTNERS.toString();

        } else if (id == R.id.nav_conduct) {
            tag = FragmentTags.CONDUCT.toString();

        } else if (id == R.id.nav_about) {
            showAboutDialog();
        }

        if (tag != null) {
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
                    collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));
                    fragment = new HomeFragment();
                    break;
                case SCHEDULE:
                    collapsingToolbarLayout.setTitle(getResources().getString(R.string.schedule));
                    fragment = new ScheduleFragment();
                    break;
                case SPEAKERS:
                    collapsingToolbarLayout.setTitle(getResources().getString(R.string.speakers));
                    fragment = new SpeakerListFragment();
                    break;
                case PARTNERS:
                    collapsingToolbarLayout.setTitle(getResources().getString(R.string.partners));
                    fragment = new PartnersFragment();
                    break;
                case CONDUCT:
                    collapsingToolbarLayout.setTitle(getResources().getString(R.string.title_conduct));
                    fragment = new ConductFragment();
                    break;
            }
        }

        Bundle payload = new Bundle();
        payload.putString(FirebaseAnalytics.Param.VALUE, tag);
        firebaseAnalytics.logEvent("fragmentViewed", payload);

        return fragment;
    }

    protected boolean showAboutDialog() {
        Bundle payload = new Bundle();
        payload.putString(FirebaseAnalytics.Param.VALUE, "LimeRobot!");
        firebaseAnalytics.logEvent("aboutViewed", payload);
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AppTheme_PopupOverlay));
        builder.setPositiveButton(R.string.okButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing other than close dialog
            }
        });
        View layout = getLayoutInflater().inflate(R.layout.about, null);
        builder.setView(layout);
        builder.show();

        return true;
    }

    @Override
    public void refreshFragment(Fragment fragment) {
        FragmentTags fragmentTag = FragmentTags.valueOf(fragment.getTag());

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
        switch (FragmentTags.valueOf(getSupportFragmentManager().findFragmentById(R.id.fragment_container).getTag())) {
            case HOME:
                ((HomeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container)).populateSpeakers(speakers);
                break;
            case SPEAKERS:
                ((SpeakerListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container)).populateSpeakers(speakers);
                break;
        }
    }

    public void pushSchedule (List<Schedule> schedules) {
        switch (FragmentTags.valueOf(getSupportFragmentManager().findFragmentById(R.id.fragment_container).getTag())) {
            case SCHEDULE:
                List<Timeslot> timeslots = schedules.get(0).getTimeslots();
                ((ScheduleFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container)).populateTimeslots(timeslots);
                break;
        }
    }

    public void refreshFinished() {

        swipeRefreshLayout.setRefreshing(false);
        refreshFragment(getSupportFragmentManager().findFragmentById(R.id.fragment_container));
    }

    public void retrofitFailed(String error) {
        Bundle payload = new Bundle();
        payload.putString(FirebaseAnalytics.Param.VALUE, error);
        firebaseAnalytics.logEvent("serviceEvents", payload);
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

        if (session.getSpeakerList().size() > 0)
            payload.putString(FirebaseAnalytics.Param.VALUE, session.getSpeakerList().get(0).getName());
        else
            payload.putString(FirebaseAnalytics.Param.VALUE, session.getTitle());

        //session.getAttending hasn't changed yet so report opposite.
        if (session.getAttending() != null && session.getAttending())
            firebaseAnalytics.logEvent("stoppedAttending", payload);
        else
            firebaseAnalytics.logEvent("isAttending", payload);

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
        Bundle payload = new Bundle();
        payload.putString(FirebaseAnalytics.Param.VALUE, speaker.getName());
        firebaseAnalytics.logEvent("speakerViewed", payload);
        startActivity(intent);
    }

    public void openGPlus(View view) {
        String profile = (String) view.getTag();
        Bundle payload = new Bundle();
        payload.putString(FirebaseAnalytics.Param.VALUE, profile);
        firebaseAnalytics.logEvent("gplusViewed", payload);
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
        Bundle payload = new Bundle();
        payload.putString(FirebaseAnalytics.Param.VALUE, twitterName);
        firebaseAnalytics.logEvent("twitterViewed", payload);
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("twitter://user?screen_name=" + twitterName)));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://twitter.com/#!/" + twitterName)));
        }
    }

    public void openWebpage(View view) {
        String tag = (String) view.getTag();
        Bundle payload = new Bundle();
        if (tag.length() > 36)
            payload.putString(FirebaseAnalytics.Param.VALUE, tag.substring(0, 36));
        else
            payload.putString(FirebaseAnalytics.Param.VALUE, tag);
        firebaseAnalytics.logEvent("webpageViewed", payload);
        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(tag)));
    }

    public void beExcellent(View view) {
        MediaPlayer.create(getApplicationContext(), R.raw.be_excellent).start();
        Bundle payload = new Bundle();
        payload.putString(FirebaseAnalytics.Param.VALUE, "excellent!");
        firebaseAnalytics.logEvent("easterEgg", payload);
    }
}
