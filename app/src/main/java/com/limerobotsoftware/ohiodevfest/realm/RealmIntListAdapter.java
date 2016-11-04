package com.limerobotsoftware.ohiodevfest.realm;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import io.realm.RealmList;

/**
 * Created by andy on 10/27/16.
 */

public class RealmIntListAdapter extends TypeAdapter<RealmList<RealmIntList>> {
    @Override
    public void write(JsonWriter out, RealmList<RealmIntList> value) throws IOException {

    }

    @Override
    public RealmList<RealmIntList> read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        RealmList<RealmIntList> realmIntLists = new RealmList<>();
        in.beginArray();
        while (in.hasNext()) {
            RealmList<RealmInt> realmInts = new RealmList<RealmInt>();
            in.beginArray();
            while (in.hasNext()) {
                realmInts.add(new RealmInt(in.nextInt()));
            }
            in.endArray();
            RealmIntList realmIntList = new RealmIntList();
            realmIntList.setVal(realmInts);
            realmIntLists.add(realmIntList);
        }
        in.endArray();
        return realmIntLists;
    }
}
