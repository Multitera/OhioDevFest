package com.limerobotsoftware.ohiodevfest.ui;

import com.limerobotsoftware.ohiodevfest.Model;
import com.limerobotsoftware.ohiodevfest.model.Schedule;
import com.limerobotsoftware.ohiodevfest.model.Session;
import com.limerobotsoftware.ohiodevfest.model.Speaker;

import java.util.List;

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
        modelSubscriptions.add(model.downloadSchedulesFromNetwork().subscribe(list -> {
            model.insertSchedule((List<Schedule>) list);
            scheduleFinished = true;
            checkAllFinished();
        }));
        modelSubscriptions.add(model.downloadSessionsFromNetwork().subscribe(list -> {
            model.insertSessions((List<Session>) list);
            sessionFinished = true;
            checkAllFinished();
        }));
        modelSubscriptions.add(model.downloadSpeakersFromNetwork().subscribe(list -> {
            model.insertSpeakers((List<Speaker>) list);
            speakerFinished = true;
            checkAllFinished();
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
}
