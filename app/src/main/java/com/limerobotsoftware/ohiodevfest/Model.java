package com.limerobotsoftware.ohiodevfest;

import com.limerobotsoftware.ohiodevfest.model.Schedule;
import com.limerobotsoftware.ohiodevfest.model.Session;
import com.limerobotsoftware.ohiodevfest.model.Speaker;
import com.limerobotsoftware.ohiodevfest.model.Timeslot;
import com.limerobotsoftware.ohiodevfest.service.OhioDevFestService;
import com.limerobotsoftware.ohiodevfest.utils.GsonHelper;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmObject;
import io.realm.RealmResults;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andy on 10/26/16.
 */

public class Model implements Closeable{

    private final Realm realm;
    private static Model instance = null;
    private OhioDevFestService festService;
    private RealmAsyncTask scheduleTask;
    private RealmAsyncTask sessionsTask;
    private RealmAsyncTask speakersTask;

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

    public void insertSchedule (List<Schedule> list) {
        scheduleTask = Realm.getDefaultInstance().executeTransactionAsync(realm -> realm.insertOrUpdate(list));
    }

    public Observable downloadSchedulesFromNetwork() {
        return festService.schedule()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void insertSpeakers (List<Speaker> list) {
        speakersTask = Realm.getDefaultInstance().executeTransactionAsync(realm -> realm.insertOrUpdate(list));
    }

    public Observable downloadSpeakersFromNetwork() {
        return festService.speakers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void insertSessions (List<Session> list) {
        sessionsTask = Realm.getDefaultInstance().executeTransactionAsync(realm -> realm.insertOrUpdate(list));
    }

    public Observable downloadSessionsFromNetwork() {
        return festService.sessions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<RealmObject> findSchedule(String date){
        return realm.where(Schedule.class)
                .equalTo("date", date)
                .findFirstAsync()
                .asObservable().filter(Schedule::isLoaded);
    }

    public Observable<RealmResults<Session>> findSessions(Integer[] ids){
        if (ids == null)
        return realm.where(Session.class)
                .findAllAsync()
                .asObservable()
                .filter(RealmResults::isLoaded);
        else
            return realm.where(Session.class)
                    .in("id", ids)
                    .findAllAsync()
                    .asObservable()
                    .filter(RealmResults::isLoaded);
    }

    public Observable<RealmResults<Speaker>> findSpeakers(Boolean featured, Integer[] ids){
        if (ids != null) {
            return realm.where(Speaker.class)
                    .in("id",ids)
                    .findAllAsync()
                    .asObservable()
                    .filter(RealmResults::isLoaded);
        } else if (featured)
            return realm.where(Speaker.class)
                    .equalTo("featured", true)
                    .findAllAsync()
                    .asObservable()
                    .filter(RealmResults::isLoaded);
        else
            return realm.where(Speaker.class)
                .findAllAsync()
                .asObservable()
                .filter(RealmResults::isLoaded);
    }

    public void addSessionsToTimeSlots() {
        realm.executeTransactionAsync(realm -> {
            List<Timeslot> timeSlots = realm.copyToRealmOrUpdate(realm.where(Timeslot.class).findAll());
            for(Timeslot timeslot : timeSlots) {
                for (int i=0; i < timeslot.getSessions().size(); i++) {
                    int val = timeslot.getSessions().get(i).getVal().get(0).getVal();
                    timeslot.getSessionList()
                            .add(realm.where(Session.class)
                                    .equalTo("id", val)
                                    .findFirst());
                }
            }
        });
    }

    public void addSessionSpeakerRelationships() {
        realm.executeTransactionAsync(realm -> {
            List<Session> sessions = realm.copyToRealmOrUpdate(realm.where(Session.class).findAll());
            for(Session session : sessions) {
                if (session.getSpeakers().size() > 0) {
                    int val = session.getSpeakers().get(0).getVal();
                    Speaker speaker = realm.copyToRealmOrUpdate(realm.where(Speaker.class).equalTo("id", val).findFirst());
                    speaker.getSessionList().add(session);
                    session.getSpeakerList().add(speaker);
                }
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
        scheduleTask.cancel();
        sessionsTask.cancel();
        speakersTask.cancel();
        realm.close();
    }
}
