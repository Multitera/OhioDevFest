package com.limerobotsoftware.ohiodevfest.realm;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import io.realm.RealmIntListRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by andy on 10/25/16.
 */
@Parcel(implementations = { RealmIntListRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { RealmIntList.class })
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

    @ParcelPropertyConverter(RealmListParcelConverter.class)
    public void setVal(RealmList<RealmInt> val) {
        this.val = val;
    }
}
