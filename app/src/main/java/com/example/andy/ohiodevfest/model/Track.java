package com.example.andy.ohiodevfest.model;

import io.realm.RealmObject;

/**
 * Created by andy on 10/25/16.
 */

public class Track  extends RealmObject {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
