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
    private CompositeSubscription subscriptions = new CompositeSubscription();

    public Presenter(MainActivity view, Model model) {
        this.view = view;
        this.model = model;
        subscriptions = model.updateData();
    }
}
