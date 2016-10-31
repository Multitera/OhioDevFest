package com.example.andy.ohiodevfest.utils;

import com.example.andy.ohiodevfest.MainActivity;
import com.example.andy.ohiodevfest.Model;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by andy on 10/27/16.
 */

public class Presenter {

    private final MainActivity view;
    private final Model model;
    private CompositeSubscription modelSubscriptions = new CompositeSubscription();
    private CompositeSubscription viewSubscriptions = new CompositeSubscription();

    public Presenter(MainActivity view, Model model) {
        this.view = view;
        this.model = model;
        if (modelSubscriptions.hasSubscriptions())
            modelSubscriptions.unsubscribe();
        modelSubscriptions = model.updateData();
    }

    public void getSpeakers(Boolean featured) {
        if (viewSubscriptions.hasSubscriptions())
            viewSubscriptions.clear();
        viewSubscriptions.add(model.findSpeakers(featured).subscribe(view::pushSpeakers));
    }

    public void onPause() {
        modelSubscriptions.unsubscribe();
        viewSubscriptions.unsubscribe();
    }
}
