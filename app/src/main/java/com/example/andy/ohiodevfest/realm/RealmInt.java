package com.example.andy.ohiodevfest.realm;

import io.realm.RealmObject;

/**
 * Created by andy on 10/25/16.
 */

public class RealmInt extends RealmObject {
    private int val;

    public RealmInt() {
    }

    public RealmInt(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
