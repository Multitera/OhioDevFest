package com.example.andy.ohiodevfest;

import com.example.andy.ohiodevfest.model.Session;
import com.example.andy.ohiodevfest.model.Speaker;
import com.example.andy.ohiodevfest.utils.GsonHelper;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import io.realm.RealmList;

/**
 * Created by andy on 10/25/16.
 */

public class DeserializerUnitTest {

    private String sessionJson = "{\"5\": {\"id\": 5,\"title\": \"How to Train Your Computer to Talk To You\",\"description\": \"Every day brings a new story or movie about how the future will be ruled by Artificial Intelligence.  How far are we away from discovering Skynet anyway? This talk will take us on a mini adventure in AI and machine learning with Clojure.  We’ll take a look at something that is pretty easy for humans to do:  telling the difference between a question, statement, or an LOL in some chat text.   What seems deceptively easy, becomes a bit harder when you take away punctuation marks.   We’ll soon find ourselves on the twisty road of natural language processing, machine learning, and Clojure proto chat bots.\",\"language\": \"\",\"complexity\": \"\",\"presentation\": \"\",\"thumbnail\": \"\",\"videoId\": \"\",\"speakers\": [2],\"tags\": [\"\"]},\"6\": {\"id\": 6,\"title\": \"Building Android Apps with Firebase\",\"description\": \"Building an Android application is hard. Building an Android application with a backend is even harder. Firebase is a tool that helps alleviate that. In this talk, we will talk about some of the tools offered by Firebase that can help make you build a fully functional android app, complete with a backend, push notifications, analytics and more.\",\"language\": \"\",\"complexity\": \"\",\"presentation\": \"\",\"thumbnail\": \"\",\"videoId\": \"\",\"speakers\": [9],\"tags\": [\"\"]}}";
    private String speakerJson = "{\"1\": {\"id\": 1,\"featured\": true,\"name\": \"Jake Wharton\",\"title\": \"Android Developer\",\"company\": \"Square\",\"country\": \"USA\",\"photoUrl\": \"/images/people/jake_wharton.png\",\"bio\": \"Jake Wharton is an Android developer at Square working on Square Cash. He has been living with a severe allergy to boilerplate code and bad APIs for years and speaks at conferences all around the world in an effort to educate more about this terrible disease.\",\"tags\": [\"Android\"],\"socials\": [{\"name\": \"Twitter\",\"icon\": \"twitter\",\"link\": \"https://twitter.com/jakewharton/\"}]},\"2\": {\"id\": 2,\"featured\": true,\"name\": \"Carin Meier\",\"title\": \"Developer\",\"company\": \"Cognitect\",\"country\": \"USA\",\"photoUrl\": \"/images/people/carin_meier.jpg\",\"bio\": \"Carin started off as a professional ballet dancer, studied Physics in college, and has been developing software for both the enterprise and entrepreneur ever since. She has a thing for Clojure and can be usually found with a cup of tea in her hand, hacking on her Roomba, AR Drone, or any other robots that happen to be nearby. She hails from Cincinnati, where she works remotely as a developer for Cognitect, and also helps organize the Cincinnati Functional Programmers user group.\",\"tags\": [\"Artificial Intelligence\", \"Machine Learning\"],\"socials\": [{\"name\": \"Twitter\",\"icon\": \"twitter\",\"link\": \"https://twitter.com/gigasquid/\"}]}}";

    @Test
    public void SessionListDeserializerTest() throws Exception {
        RealmList<Session> sessions = GsonHelper.getCustomGson().fromJson(sessionJson, new TypeToken<RealmList<Session>>(){}.getType());
        assert (sessions.isEmpty() == false);
    }
    @Test
    public void SpeakerListDeserializerTest() throws Exception {
        RealmList<Speaker> speakers = GsonHelper.getCustomGson().fromJson(speakerJson, new TypeToken<RealmList<Speaker>>(){}.getType());
        assert (speakers.isEmpty() == false);
    }
}
