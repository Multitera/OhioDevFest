package com.limerobotsoftware.ohiodevfest.realm;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.RealmStringRealmProxy;

/**
 * Created by andy on 10/25/16.
 */

@Parcel(implementations = { RealmStringRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { RealmString.class })
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
