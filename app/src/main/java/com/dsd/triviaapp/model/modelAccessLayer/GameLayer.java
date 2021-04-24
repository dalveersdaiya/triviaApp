package com.dsd.triviaapp.model.modelAccessLayer;

import com.dsd.triviaapp.helper.DateTimeHelper;
import com.dsd.triviaapp.helper.StringHelper;
import com.dsd.triviaapp.model.Game;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.List;


/**
 * To access the db layer with minimal direct interaction with the db model
 */
public class GameLayer extends DataLayerHelper {

    public static final GameLayer getInstance() {
        return new GameLayer();
    }

    public static void deleteData() {
        Game.deleteData();
    }

    public static void deleteByIds(String[] ids) {
        Game.deleteByIds(ids);
    }

    public static void delete() {
        delete(Game.class);
    }

    public static Game getById(String id) {
        return Game.getById(id);
    }

    public static List<Game> getAll() {
        return Game.getAll();
    }

    public void setParams(JsonObject params) {
        this.params = params;
    }

    public <T> T getParams(Class<T> type) {
        return new Gson().fromJson(params.toString(), type);
    }

    private JsonObject params;

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    }

    public static void createGame(OnRealmTransactionCompleted onRealmTransactionCompleted) {
        String id = StringHelper.uniqueId24();
        openRealmTransaction();
        Game game = createObject(Game.class, id);
        copyToRealmOrUpdate(game);
        closeAndCommitTransaction();;
        if (onRealmTransactionCompleted != null) {
            onRealmTransactionCompleted.onRealmTransactionCompleted(id);
        }
    }

    public static void createGame(String id, OnRealmTransactionCompleted onRealmTransactionCompleted) {
        openRealmTransaction();
        Game game = createObject(Game.class, id);
        copyToRealmOrUpdate(game);
        closeAndCommitTransaction();;
        if (onRealmTransactionCompleted != null) {
            onRealmTransactionCompleted.onRealmTransactionCompleted(id);
        }
    }

    public static void updateGameName(String id, String name, OnRealmTransactionCompleted onRealmTransactionCompleted) {
        openRealmTransaction();
        Game game = getById(id);
        if(game != null){
            game.setName(name);
            game.setTimeStamp(0);
        }
        if (onRealmTransactionCompleted != null) {
            onRealmTransactionCompleted.onRealmTransactionCompleted("Success");
        }
    }

    public static void updateGameBestCricketer(String id, String bestCricketer, OnRealmTransactionCompleted onRealmTransactionCompleted) {
        openRealmTransaction();
        Game game = getById(id);
        if(game != null){
            game.setBestCricketer(bestCricketer);
            game.setTimeStamp(0);
        }
        if (onRealmTransactionCompleted != null) {
            onRealmTransactionCompleted.onRealmTransactionCompleted("Success");
        }
    }

    public static void updateGameFlagColors(String id, String updateGameFlagColors, OnRealmTransactionCompleted onRealmTransactionCompleted) {
        openRealmTransaction();
        Game game = getById(id);
        if(game != null){
            game.setFlagColors(updateGameFlagColors);
            game.setTimeStamp(DateTimeHelper.getCurrentDateTimeInUnixTimeStampMillis());
        }
        if (onRealmTransactionCompleted != null) {
            onRealmTransactionCompleted.onRealmTransactionCompleted("Success");
        }
    }


}
