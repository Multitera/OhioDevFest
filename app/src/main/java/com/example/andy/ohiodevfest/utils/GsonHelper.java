package com.example.andy.ohiodevfest.utils;

import com.example.andy.ohiodevfest.model.Session;
import com.example.andy.ohiodevfest.model.Speaker;
import com.example.andy.ohiodevfest.realm.RealmExclusionStrategy;
import com.example.andy.ohiodevfest.realm.RealmInt;
import com.example.andy.ohiodevfest.realm.RealmIntAdapter;
import com.example.andy.ohiodevfest.realm.RealmString;
import com.example.andy.ohiodevfest.realm.RealmStringAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import io.realm.RealmList;

/**
 * Created by andy on 10/25/16.
 */

public class GsonHelper {

    public static Gson getCustomGson() {
        return new GsonBuilder().setExclusionStrategies(new RealmExclusionStrategy())
                .registerTypeAdapter(new TypeToken<RealmList<RealmString>>(){}.getType(), new RealmStringAdapter())
                .registerTypeAdapter(new TypeToken<RealmList<RealmInt>>(){}.getType(), new RealmIntAdapter())
                .registerTypeAdapter(new TypeToken<RealmList<Session>>(){}.getType(), new SessionListDeserializer())
                .registerTypeAdapter(new TypeToken<RealmList<Speaker>>(){}.getType(), new SpeakerListDeserializer())
                .create();
    }
}
