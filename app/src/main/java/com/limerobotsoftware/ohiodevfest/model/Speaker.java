package com.limerobotsoftware.ohiodevfest.model;

import com.limerobotsoftware.ohiodevfest.realm.RealmListParcelConverter;
import com.limerobotsoftware.ohiodevfest.realm.RealmString;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.SpeakerRealmProxy;
import io.realm.annotations.PrimaryKey;

/**
 * Created by andy on 10/25/16.
 */

@Parcel(implementations = { SpeakerRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Speaker.class })
public class Speaker  extends RealmObject {

    @PrimaryKey
    private int id;
    private boolean featured;
    private String name;
    private String title;
    private String company;
    private String country;
    private String photoUrl;
    private String bio;
    private RealmList<RealmString> tags;
    private RealmList<Social> socials;

    public int getId() {
        return id;
    }

    public boolean isFeatured() {
        return featured;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getCountry() {
        return country;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getBio() {
        return bio;
    }

    public RealmList<RealmString> getTags() {
        return tags;
    }

    public RealmList<Social> getSocials() {
        return socials;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @ParcelPropertyConverter(RealmListParcelConverter.class)
    public void setTags(RealmList<RealmString> tags) {
        this.tags = tags;
    }

    @ParcelPropertyConverter(RealmListParcelConverter.class)
    public void setSocials(RealmList<Social> socials) {
        this.socials = socials;
    }
}
