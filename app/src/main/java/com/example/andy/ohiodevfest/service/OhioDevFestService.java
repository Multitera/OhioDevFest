package com.example.andy.ohiodevfest.service;

import com.example.andy.ohiodevfest.model.Schedule;
import com.example.andy.ohiodevfest.model.Session;
import com.example.andy.ohiodevfest.model.Speaker;

import io.realm.RealmList;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by andy on 10/26/16.
 */

public interface OhioDevFestService {
    @GET("data/speakers.json")
    Observable<RealmList<Speaker>> speakers();

    @GET("data/sessions.json")
    Observable<RealmList<Session>> sessions();

    @GET("data/schedule.json")
    Observable<RealmList<Schedule>> schedule();
}
