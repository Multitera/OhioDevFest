package com.limerobotsoftware.ohiodevfest.utils;

import com.limerobotsoftware.ohiodevfest.model.Session;
import com.limerobotsoftware.ohiodevfest.model.Speaker;
import com.limerobotsoftware.ohiodevfest.realm.RealmExclusionStrategy;
import com.limerobotsoftware.ohiodevfest.realm.RealmInt;
import com.limerobotsoftware.ohiodevfest.realm.RealmIntAdapter;
import com.limerobotsoftware.ohiodevfest.realm.RealmIntList;
import com.limerobotsoftware.ohiodevfest.realm.RealmIntListAdapter;
import com.limerobotsoftware.ohiodevfest.realm.RealmString;
import com.limerobotsoftware.ohiodevfest.realm.RealmStringAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by andy on 10/25/16.
 */

public class GsonHelper {

    public static Gson getCustomGson() {
        return new GsonBuilder().setExclusionStrategies(new RealmExclusionStrategy())
                .registerTypeAdapter(new TypeToken<RealmList<RealmString>>(){}.getType(), new RealmStringAdapter())
                .registerTypeAdapter(new TypeToken<RealmList<RealmInt>>(){}.getType(), new RealmIntAdapter())
                .registerTypeAdapter(new TypeToken<RealmList<RealmIntList>>(){}.getType(), new RealmIntListAdapter())
                .registerTypeAdapter(new TypeToken<List<Session>>(){}.getType(), new SessionListDeserializer())
                .registerTypeAdapter(new TypeToken<List<Speaker>>(){}.getType(), new SpeakerListDeserializer())
                .create();
    }
}
