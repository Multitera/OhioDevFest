package com.example.andy.ohiodevfest;

import com.example.andy.ohiodevfest.model.Schedule;
import com.example.andy.ohiodevfest.model.Session;
import com.example.andy.ohiodevfest.model.Speaker;
import com.example.andy.ohiodevfest.service.OhioDevFestService;

import java.io.Closeable;
import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by andy on 10/26/16.
 */

public class Model implements Closeable{

    private final Realm realm;
    private static Model instance = null;
    private OhioDevFestService festService;

    public static synchronized Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    public Model() {
        realm = Realm.getDefaultInstance();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://ohiodevfest.com/").build();
        festService = retrofit.create(OhioDevFestService.class);
        updateData();
    }

    public void updateData (){
        festService.schedule()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Schedule>() {
                    @Override
                    public void call(Schedule schedule) {
                        realm.insertOrUpdate(schedule);
                    }
                });
        festService.sessions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RealmList<Session>>() {
                    @Override
                    public void call(RealmList<Session> sessions) {
                        realm.insertOrUpdate(sessions);
                    }
                });
        festService.speakers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RealmList<Speaker>>() {
                    @Override
                    public void call(RealmList<Speaker> speakers) {
                        realm.insertOrUpdate(speakers);
                    }
                });
    }

    public Observable<RealmResults<Schedule>> getSchedule(){
        return realm.where(Schedule.class)
                .findAllAsync()
                .asObservable()
                .filter(new Func1<RealmResults<Schedule>, Boolean>() {
                    @Override
                    public Boolean call(RealmResults<Schedule> schedules) {
                        return schedules.isLoaded();
                    }
                });
    }

    public Observable<RealmResults<Session>> getSessions(){
        return realm.where(Session.class)
                .findAllAsync()
                .asObservable()
                .filter(new Func1<RealmResults<Session>, Boolean>() {
                    @Override
                    public Boolean call(RealmResults<Session> sessions) {
                        return sessions.isLoaded();
                    }
                });
    }

    public Observable<RealmResults<Speaker>> getSpeakers(){
        return realm.where(Speaker.class)
                .findAllAsync()
                .asObservable()
                .filter(new Func1<RealmResults<Speaker>, Boolean>() {
                    @Override
                    public Boolean call(RealmResults<Speaker> speakers) {
                        return speakers.isLoaded();
                    }
                });
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }

    @Override
    public void close() throws IOException {
        realm.close();
    }
}
