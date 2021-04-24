package com.dsd.triviaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.Sort;
import io.realm.annotations.PrimaryKey;

public class Game extends RealmObject {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("timestamp")
    @Expose
    private long timeStamp;

    @SerializedName("bestCricketer")
    @Expose
    private String bestCricketer;

    @SerializedName("flagColors")
    @Expose
    private String flagColors;

    public static Game getById(String id) {
        return Realm.getDefaultInstance().where(Game.class).equalTo("id", id).findFirst();
    }

    public static List<Game> getAll() {
        return Realm.getDefaultInstance().where(Game.class).notEqualTo("timeStamp", 0).sort("timeStamp", Sort.DESCENDING).findAll();
    }

    public static void deleteByIds(String[] ids) {
        Realm.getDefaultInstance().where(Game.class).in("id", ids).findAll().deleteAllFromRealm();
    }

    public static void deleteData() {
        Realm.getDefaultInstance().where(Game.class).findAll().deleteAllFromRealm();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getBestCricketer() {
        return bestCricketer;
    }

    public void setBestCricketer(String bestCricketer) {
        this.bestCricketer = bestCricketer;
    }

    public String getFlagColors() {
        return flagColors;
    }

    public void setFlagColors(String flagColors) {
        this.flagColors = flagColors;
    }
}
