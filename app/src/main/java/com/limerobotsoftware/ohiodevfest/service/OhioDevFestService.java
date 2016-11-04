package com.limerobotsoftware.ohiodevfest.service;

import com.limerobotsoftware.ohiodevfest.model.Schedule;
import com.limerobotsoftware.ohiodevfest.model.Session;
import com.limerobotsoftware.ohiodevfest.model.Speaker;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by andy on 10/26/16.
 */

public interface OhioDevFestService {
    @GET("data/speakers.json")
    Observable<List<Speaker>> speakers();

    @GET("data/sessions.json")
    Observable<List<Session>> sessions();

    @GET("data/schedule.json")
    Observable<List<Schedule>> schedule();
}
