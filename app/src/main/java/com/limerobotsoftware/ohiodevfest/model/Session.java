package com.limerobotsoftware.ohiodevfest.model;

import com.limerobotsoftware.ohiodevfest.realm.RealmListParcelConverter;
import com.limerobotsoftware.ohiodevfest.realm.RealmInt;
import com.limerobotsoftware.ohiodevfest.realm.RealmString;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.SessionRealmProxy;
import io.realm.annotations.PrimaryKey;

/**
 * Created by andy on 10/25/16.
 */
@Parcel(implementations = { SessionRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Session.class })
public class Session  extends RealmObject {

    @PrimaryKey
    private int id;
    private String title;
    private String description;
    private String language;
    private String complexity;
    private String presentation;
    private String thumbnail;
    private String videoId;
    private RealmList<RealmInt> speakers;
    private RealmList<RealmString> tags;
    private RealmList<Speaker> speakerList;

    public RealmList<Speaker> getSpeakerList() {
        return speakerList;
    }

    @ParcelPropertyConverter(RealmListParcelConverter.class)
    public void setSpeakerList(RealmList<Speaker> speakerList) {
        this.speakerList = speakerList;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public String getComplexity() {
        return complexity;
    }

    public String getPresentation() {
        return presentation;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getVideoId() {
        return videoId;
    }

    public RealmList<RealmInt> getSpeakers() {
        return speakers;
    }

    public RealmList<RealmString> getTags() {
        return tags;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @ParcelPropertyConverter(RealmListParcelConverter.class)
    public void setSpeakers(RealmList<RealmInt> speakers) {
        this.speakers = speakers;
    }

    @ParcelPropertyConverter(RealmListParcelConverter.class)
    public void setTags(RealmList<RealmString> tags) {
        this.tags = tags;
    }
}
