package com.example.andy.ohiodevfest.realm;

import io.realm.RealmObject;

/**
 * Created by andy on 10/25/16.
 */

public class RealmString extends RealmObject {
    private String val;

    public RealmString() {
    }

    public RealmString(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
