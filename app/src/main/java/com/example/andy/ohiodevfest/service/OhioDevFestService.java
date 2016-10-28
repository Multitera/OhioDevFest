package com.example.andy.ohiodevfest.service;

import com.example.andy.ohiodevfest.model.Schedule;
import com.example.andy.ohiodevfest.model.Session;
import com.example.andy.ohiodevfest.model.Speaker;

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
