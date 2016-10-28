package com.example.andy.ohiodevfest;

import com.example.andy.ohiodevfest.model.Schedule;
import com.example.andy.ohiodevfest.model.Session;
import com.example.andy.ohiodevfest.model.Speaker;
import com.example.andy.ohiodevfest.service.OhioDevFestService;
import com.example.andy.ohiodevfest.utils.GsonHelper;

import java.io.Closeable;
import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ohiodevfest.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonHelper.getCustomGson()))
                .build();
        festService = retrofit.create(OhioDevFestService.class);
    }

    public Subscription downloadSchedulesFromNetwork() {
        return festService.schedule()
                .subscribeOn(Schedulers.io())
                .subscribe(list -> {
                    Realm.getDefaultInstance().executeTransaction(realm -> realm.insertOrUpdate(list));
                });
    }

    public Subscription downloadSpeakersFromNetwork() {
        return festService.speakers()
                .subscribeOn(Schedulers.io())
                .subscribe(list -> {
                    Realm.getDefaultInstance().executeTransaction(realm -> realm.insertOrUpdate(list));
                });
    }

    public Subscription downloadSessionsFromNetwork() {
        return festService.sessions()
                .subscribeOn(Schedulers.io())
                .subscribe(list -> {
                    Realm.getDefaultInstance().executeTransaction(realm -> realm.insertOrUpdate(list));
                });
    }

    public CompositeSubscription updateData (){
        CompositeSubscription subscriptions = new CompositeSubscription();
        subscriptions.add(downloadSchedulesFromNetwork());
        subscriptions.add(downloadSessionsFromNetwork());
        subscriptions.add(downloadSpeakersFromNetwork());
        return subscriptions;
    }

/*    public Subscription readFromRealm() {
        return realm.where(SomeObject.class)
                .findAllAsync()
                .asObservable()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(RealmResults::isLoaded)
                .subscribe(objects -> adapter.updateData(objects));
    }*/

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
