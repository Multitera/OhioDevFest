package com.example.andy.ohiodevfest.realm;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import io.realm.RealmList;

/**
 * Created by andy on 10/25/16.
 */

public class RealmIntAdapter extends TypeAdapter<RealmList<RealmInt>> {
    @Override
    public void write(JsonWriter out, RealmList<RealmInt> value) throws IOException {

    }

    @Override
    public RealmList<RealmInt> read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        RealmList<RealmInt> list = new RealmList<RealmInt>();
        in.beginArray();
        while (in.hasNext()) {
            list.add(new RealmInt(in.nextInt()));
        }
        in.endArray();
        return list;
    }
}
