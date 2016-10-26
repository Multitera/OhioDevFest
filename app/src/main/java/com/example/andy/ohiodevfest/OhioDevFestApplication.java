package com.example.andy.ohiodevfest;

import android.app.Application;
import android.util.Log;

import io.realm.BuildConfig;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.log.RealmLog;

/**
 * Created by andy on 10/25/16.
 */

public class OhioDevFestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfig);

        // Enable full log output when debugging
        if (BuildConfig.DEBUG) {
            RealmLog.setLevel(Log.VERBOSE);
        }
    }
}
