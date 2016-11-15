package com.limerobotsoftware.ohiodevfest.ui;

import com.limerobotsoftware.ohiodevfest.Model;
import com.limerobotsoftware.ohiodevfest.model.Schedule;
import com.limerobotsoftware.ohiodevfest.model.Session;
import com.limerobotsoftware.ohiodevfest.model.Speaker;

import java.util.List;

import rx.Observer;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by andy on 10/27/16.
 */

public class Presenter {

    private final MainActivity view;
    private final Model model;
    private Boolean scheduleFinished;
    private Boolean sessionFinished;
    private Boolean speakerFinished;
    private CompositeSubscription modelSubscriptions = new CompositeSubscription();
    private CompositeSubscription viewSubscriptions = new CompositeSubscription();

    public Presenter(MainActivity view, Model model) {
        this.view = view;
        this.model = model;
        if (modelSubscriptions == null)
            modelSubscriptions = new CompositeSubscription();
        refreshData();
    }

    public void refreshData() {
        if (modelSubscriptions.hasSubscriptions())
            modelSubscriptions.clear();
        scheduleFinished = false;
        sessionFinished = false;
        speakerFinished = false;
        modelSubscriptions.add(model.downloadSchedulesFromNetwork().subscribe(new Observer() {
            @Override
            public void onCompleted() {
                scheduleFinished = true;
                checkAllFinished();
            }

            @Override
            public void onError(Throwable e) {
                refreshFailed(e.getLocalizedMessage());
            }

            @Override
            public void onNext(Object list) {
                model.insertSchedule((List<Schedule>) list);
            }
        }));
        modelSubscriptions.add(model.downloadSessionsFromNetwork().subscribe(new Observer() {
            @Override
            public void onCompleted() {
                sessionFinished = true;
                checkAllFinished();
            }

            @Override
            public void onError(Throwable e) {
                refreshFailed(e.getLocalizedMessage());
            }

            @Override
            public void onNext(Object list) {
                model.insertSessions((List<Session>) list);
            }
        }));
        modelSubscriptions.add(model.downloadSpeakersFromNetwork().subscribe(new Observer() {
            @Override
            public void onCompleted() {
                speakerFinished = true;
                checkAllFinished();
            }

            @Override
            public void onError(Throwable e) {
                refreshFailed(e.getLocalizedMessage());
            }

            @Override
            public void onNext(Object list) {
                model.insertSpeakers((List<Speaker>) list);
            }
        }));
    }

    public void getSpeakers(Boolean featured, Integer[] ids) {
        if (viewSubscriptions.hasSubscriptions())
            viewSubscriptions.clear();
        viewSubscriptions.add(model.findSpeakers(featured, ids).subscribe(view::pushSpeakers));
    }

    public void getSchedule(String date) {
        if (viewSubscriptions.hasSubscriptions())
            viewSubscriptions.clear();
        viewSubscriptions.add(model.findSchedule(date).subscribe(view::pushSchedule));
    }

    public void updateAttending(Integer id) {
        model.toggleAttending(id);
    }

    public void onPause() {
        modelSubscriptions.unsubscribe();
        viewSubscriptions.unsubscribe();
    }

    public void checkAllFinished() {
        if (scheduleFinished&&sessionFinished&&speakerFinished) {
            view.refreshFinished();
            model.addSessionSpeakerRelationships();
            model.addSessionsToTimeSlots();
        }
    }

    private void refreshFailed(String localizedMessage) {
        modelSubscriptions.clear();
        view.retrofitFailed(localizedMessage);
    }
}
