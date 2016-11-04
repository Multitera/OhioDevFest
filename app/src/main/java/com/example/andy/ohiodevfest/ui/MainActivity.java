package com.example.andy.ohiodevfest.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
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

import com.example.andy.ohiodevfest.Model;
import com.example.andy.ohiodevfest.R;
import com.example.andy.ohiodevfest.model.Session;
import com.example.andy.ohiodevfest.model.Speaker;
import com.example.andy.ohiodevfest.ui.fragment.ConductFragment;
import com.example.andy.ohiodevfest.ui.fragment.HomeFragment;
import com.example.andy.ohiodevfest.ui.fragment.PartnersFragment;
import com.example.andy.ohiodevfest.ui.fragment.SpeakerListFragment;

import org.parceler.Parcels;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Presenter presenter = new Presenter(this, Model.getInstance());
    SwipeRefreshLayout swipeRefreshLayout;
    private final String SPEAKER_KEY = "speaker";
    private enum FragmentTags {HOME, SCHEDULE, SPEAKERS, PARTNERS, CONDUCT}
    private FragmentTags currentFragment;

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

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.refreshData();
        });

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
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new PartnersFragment(), FragmentTags.PARTNERS.toString())
                    .commit();

        } else if (id == R.id.nav_conduct) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ConductFragment(), FragmentTags.CONDUCT.toString())
                    .commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    public void pushSessions (List<Session> sessions) {
        switch (currentFragment) {
        }
    }

    public void refreshFinished () {
        swipeRefreshLayout.setRefreshing(false);
    }

    public void openSpeaker(View view) {
        Intent intent = new Intent(this, SpeakerActivity.class);
        Parcelable parcelable = Parcels.wrap(view.getTag());
        intent.putExtra(SPEAKER_KEY, parcelable);
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
