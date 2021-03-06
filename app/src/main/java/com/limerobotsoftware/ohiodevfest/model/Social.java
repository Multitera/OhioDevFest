package com.limerobotsoftware.ohiodevfest.model;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.SocialRealmProxy;

/**
 * Created by andy on 10/25/16.
 */

@Parcel(implementations = { SocialRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Social.class })
public class Social  extends RealmObject {

    private String name;
    private String icon;
    private String link;

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getLink() {
        return link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
