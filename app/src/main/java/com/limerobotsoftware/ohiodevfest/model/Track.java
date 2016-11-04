package com.limerobotsoftware.ohiodevfest.model;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.TrackRealmProxy;

/**
 * Created by andy on 10/25/16.
 */
@Parcel(implementations = { TrackRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Track.class })
public class Track  extends RealmObject {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
