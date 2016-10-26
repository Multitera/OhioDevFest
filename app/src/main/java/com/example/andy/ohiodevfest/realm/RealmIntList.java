package com.example.andy.ohiodevfest.realm;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by andy on 10/25/16.
 */

public class RealmIntList extends RealmObject {
    private RealmList<RealmInt> val;

    public RealmIntList() {
    }

    public RealmIntList(RealmList<RealmInt> val) {
        this.val = val;
    }

    public RealmList<RealmInt> getVal() {
        return val;
    }

    public void setVal(RealmList<RealmInt> val) {
        this.val = val;
    }
}
