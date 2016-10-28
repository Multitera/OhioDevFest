package com.example.andy.ohiodevfest.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by andy on 10/25/16.
 */

public class Schedule  extends RealmObject{
    @PrimaryKey
    private String date;
    private String dateReadable;
    private RealmList<Track> tracks;
    private RealmList<Timeslot> timeslots;

    public String getDate() {
        return date;
    }

    public String getDateReadable() {
        return dateReadable;
    }

    public RealmList<Track> getTracks() {
        return tracks;
    }

    public RealmList<Timeslot> getTimeslots() {
        return timeslots;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDateReadable(String dateReadable) {
        this.dateReadable = dateReadable;
    }

    public void setTracks(RealmList<Track> tracks) {
        this.tracks = tracks;
    }

    public void setTimeslots(RealmList<Timeslot> timeslots) {
        this.timeslots = timeslots;
    }
}
