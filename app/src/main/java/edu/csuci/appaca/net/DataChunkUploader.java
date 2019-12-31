package edu.csuci.appaca.net;

import android.app.Activity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.data.CurrencyManager;
import edu.csuci.appaca.data.HighScore;
import edu.csuci.appaca.data.Inventory;
import edu.csuci.appaca.data.MiniGames;
import edu.csuci.appaca.data.SavedTime;
import edu.csuci.appaca.data.StaminaManager;
import edu.csuci.appaca.data.TCGDeviceId;
import edu.csuci.appaca.data.statics.ShopData;
import edu.csuci.appaca.data.statics.StaticClothesItem;
import edu.csuci.appaca.data.statics.StaticFoodItem;
import edu.csuci.appaca.utils.ListUtils;

public class DataChunkUploader {

    private List<JSONObject> alpacas;
    private List<JSONObject> clothing;
    private List<JSONObject> food;
    private List<JSONObject> highScores;
    private JSONObject userData;
    private boolean done;

    final HttpCallback onSuccess = new HttpCallback() {
        @Override
        public void callback(int responseCode, String data) {
            process();
        }
    };

    final HttpCallback onError = new HttpCallback() {
        @Override
        public void callback(int responseCode, String data) {
            finish();
        }
    };

    private final Activity parent;

    private DataChunkUploader(Activity parent) {
        this.parent = parent;
        alpacas = new ArrayList<>();
        clothing = new ArrayList<>();
        food = new ArrayList<>();
        highScores = new ArrayList<>();
        userData = new JSONObject();
        this.done = false;
    }

    public static DataChunkUploader newUploader(Activity parent) throws JSONException {
        final DataChunkUploader uploader = new DataChunkUploader(parent);
        final String deviceId = TCGDeviceId.getDeviceId();
        AlpacaFarm.forEach(new ListUtils.Consumer<Alpaca>() {
            @Override
            public void accept(Alpaca alpaca) {
                try {
                    JSONObject alpacaJson = alpaca.toJSON();
                    alpacaJson.put("deviceId", deviceId);
                    uploader.alpacas.add(alpacaJson);
                } catch (JSONException e) {
                    Log.e(getClass().getName(), e.getMessage(), e);
                }
            }
        });

        for (StaticClothesItem clothesItem : ShopData.getAllClothes()) {
            final int id = clothesItem.id;
            final int amount = Inventory.getClothesAmount(id);
            JSONObject clothesJSON = new JSONObject();
            clothesJSON.put("deviceId", deviceId);
            clothesJSON.put("id", id);
            clothesJSON.put("amount", amount);
            uploader.clothing.add(clothesJSON);
        }

        for (StaticFoodItem foodItem : ShopData.getAllFood()) {
            final int id = foodItem.id;
            final int amount = Inventory.getFoodAmount(id);
            JSONObject foodJSON = new JSONObject();
            foodJSON.put("deviceId", deviceId);
            foodJSON.put("id", id);
            foodJSON.put("amount", amount);
            uploader.food.add(foodJSON);
        }

        for (MiniGames miniGame : MiniGames.values()) {
            final int id = miniGame.ordinal();
            final int highScore = HighScore.getHighScore(miniGame);
            JSONObject highScoreJSON = new JSONObject();
            highScoreJSON.put("deviceId", deviceId);
            highScoreJSON.put("id", id);
            highScoreJSON.put("highScore", highScore);
            uploader.highScores.add(highScoreJSON);
        }

        uploader.userData.put("deviceId", deviceId);
        uploader.userData.put("savedTime", SavedTime.lastSavedTime());
        uploader.userData.put("silver", CurrencyManager.getCurrencyOther());
        uploader.userData.put("gold", CurrencyManager.getCurrencyAlpaca());
        uploader.userData.put("stamina", StaminaManager.getCurrentStamina());
        uploader.userData.put("maxStamina", StaminaManager.getMaxStamina());
        uploader.userData.put("firstStaminaUseTime", StaminaManager.getFirstStaminaUsedTime());

        return uploader;

    }

    private void finish() {
        alpacas.clear();
        clothing.clear();
        food.clear();
        highScores.clear();
        this.done = true;
        DataUploadQueue.process(); // processes next chunk
    }

    public void process() {
        if (alpacas.size() > 0) {
            uploadNextAlpaca();
            return;
        }
        if (clothing.size() > 0) {
            uploadNextClothing();
            return;
        }
        if (food.size() > 0) {
            uploadNextFood();
            return;
        }
        if (highScores.size() > 0) {
            uploadNextHighScore();
            return;
        }
        if (!done) {
            uploadUserData();
        }
    }

    private void uploadNextAlpaca() {
        JSONObject alpaca = alpacas.remove(0);
        HttpRequestBuilder.newPost(parent.getString(R.string.webapi_base_url) + "/appaca/upload/alpaca", this.parent)
                .setBodyJSON(alpaca)
                .setOnSuccess(onSuccess)
                .setOnError(onError)
                .send();
    }

    private void uploadNextClothing() {
        JSONObject clothing = this.clothing.remove(0);
        Log.i(getClass().getName(), clothing.toString());
        HttpRequestBuilder.newPost(parent.getString(R.string.webapi_base_url) + "/appaca/upload/clothing", this.parent)
                .setBodyJSON(clothing)
                .setOnSuccess(onSuccess)
                .setOnError(onError)
                .send();
    }

    private void uploadNextFood() {
        JSONObject food = this.food.remove(0);
        HttpRequestBuilder.newPost(parent.getString(R.string.webapi_base_url) + "/appaca/upload/food", this.parent)
                .setBodyJSON(food)
                .setOnSuccess(onSuccess)
                .setOnError(onError)
                .send();
    }

    private void uploadNextHighScore() {
        JSONObject highScore = this.highScores.remove(0);
        HttpRequestBuilder.newPost(parent.getString(R.string.webapi_base_url) + "/appaca/upload/highscore", this.parent)
                .setBodyJSON(highScore)
                .setOnSuccess(onSuccess)
                .setOnError(onError)
                .send();
    }

    private void uploadUserData() {
        HttpRequestBuilder.newPost(parent.getString(R.string.webapi_base_url) + "/appaca/upload/userdata", this.parent)
                .setBodyJSON(this.userData)
                .setOnSuccess(new HttpCallback() {
                    @Override
                    public void callback(int responseCode, String data) {
                        finish();
                    }
                })
                .setOnError(onError)
                .send();
    }

}
