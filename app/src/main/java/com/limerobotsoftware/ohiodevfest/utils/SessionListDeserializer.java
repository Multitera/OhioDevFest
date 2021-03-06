package com.limerobotsoftware.ohiodevfest.utils;

import com.limerobotsoftware.ohiodevfest.model.Session;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by andy on 10/25/16.
 */

public class SessionListDeserializer implements JsonDeserializer<List<Session>> {
    @Override
    public List<Session> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Session> list = new ArrayList<>();
        if (json.isJsonObject()) {
            JsonObject object = json.getAsJsonObject();
            Gson customGson = GsonHelper.getCustomGson();
            for (Map.Entry<String,JsonElement> entry : object.entrySet()) {
                list.add(customGson.fromJson(entry.getValue(), Session.class));
            }
        }
        return list;
    }
}
