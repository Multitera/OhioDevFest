package com.limerobotsoftware.ohiodevfest;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import io.realm.BuildConfig;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.log.RealmLog;

/**
 * Created by andy on 10/25/16.
 */

public class OhioDevFestApplication extends Application {

    public static RefWatcher getRefWatcher(Context context) {
        OhioDevFestApplication application = (OhioDevFestApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);

        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfig);

        // Enable full log output when debugging
        if (BuildConfig.DEBUG) {
            RealmLog.setLevel(Log.VERBOSE);
        }
    }
}
