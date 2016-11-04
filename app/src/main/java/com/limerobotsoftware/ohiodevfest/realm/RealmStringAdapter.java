package com.limerobotsoftware.ohiodevfest.realm;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import io.realm.RealmList;

/**
 * Created by andy on 10/25/16.
 */

public class RealmStringAdapter extends TypeAdapter<RealmList<RealmString>> {
    @Override
    public void write(JsonWriter out, RealmList<RealmString> value) throws IOException {

    }

    @Override
    public RealmList<RealmString> read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        RealmList<RealmString> list = new RealmList<RealmString>();
        in.beginArray();
        while (in.hasNext()) {
            list.add(new RealmString(in.nextString()));
        }
        in.endArray();
        return list;
    }
}
