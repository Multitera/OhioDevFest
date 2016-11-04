package com.limerobotsoftware.ohiodevfest.realm;

import org.parceler.Parcel;

import io.realm.RealmIntRealmProxy;
import io.realm.RealmObject;

/**
 * Created by andy on 10/25/16.
 */
@Parcel(implementations = { RealmIntRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { RealmInt.class })
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
