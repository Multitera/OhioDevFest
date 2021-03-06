package com.limerobotsoftware.ohiodevfest.model;

import com.limerobotsoftware.ohiodevfest.realm.RealmIntList;
import com.limerobotsoftware.ohiodevfest.realm.RealmListParcelConverter;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.TimeslotRealmProxy;

/**
 * Created by andy on 10/25/16.
 */
@Parcel(implementations = { TimeslotRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Timeslot.class })
public class Timeslot  extends RealmObject {

    private String startTime;
    private String endTime;
    private RealmList<RealmIntList> sessions;
    private RealmList<Session> sessionList;

    public RealmList<Session> getSessionList() {
        return sessionList;
    }

    @ParcelPropertyConverter(RealmListParcelConverter.class)
    public void setSessionList(RealmList<Session> sessionList) {
        this.sessionList = sessionList;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public RealmList<RealmIntList> getSessions() {
        return sessions;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @ParcelPropertyConverter(RealmListParcelConverter.class)
    public void setSessions(RealmList<RealmIntList> sessions) {
        this.sessions = sessions;
    }
}
