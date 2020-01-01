package edu.csuci.appaca.net;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.csuci.appaca.activities.MainActivity;
import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.data.CurrencyManager;
import edu.csuci.appaca.data.HighScore;
import edu.csuci.appaca.data.Inventory;
import edu.csuci.appaca.data.SaveDataUtils;
import edu.csuci.appaca.data.SavedTime;
import edu.csuci.appaca.data.StaminaManager;
import edu.csuci.appaca.data.TCGAccount;
import edu.csuci.appaca.data.TCGDeviceId;

public class DataSync {

    public static void forceDownload(final long lastLocalSavedTime, final Activity parent) {
        syncData(lastLocalSavedTime, parent, true);
    }

    public static void syncData(final long lastLocalSavedTime, final Activity parent) {
        syncData(lastLocalSavedTime, parent, false);
    }

    private static void syncData(final long lastLocalSavedTime, final Activity parent, final boolean forceDownload) {
        if (TCGDeviceId.getDeviceId().length() == 0 || !TCGAccount.isLoggedIn()) return;
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = DataDownloader.downloadDataBlocking(parent);
                        if (jsonObject == null) return;
                        try {
                            final long cloudSavedTime = jsonObject.getLong("savedTime");
                            if(lastLocalSavedTime < cloudSavedTime || forceDownload) { // local save is older
                                JSONObject newDataObject = new JSONObject();

                                newDataObject.put("alpacas", jsonObject.getJSONArray("alpacas"));
                                AlpacaFarm.openFarm();
                                AlpacaFarm.clear();
                                AlpacaFarm.load(newDataObject);
                                AlpacaFarm.closeFarm();
                                if(parent instanceof MainActivity) {
                                    ((MainActivity) parent).updateName();
                                }

                                newDataObject.put("food", jsonObject.getJSONArray("foodInventory"));
                                newDataObject.put("clothes", jsonObject.getJSONArray("clothingInventory"));
                                Inventory.clear();
                                Inventory.load(newDataObject);

                                JSONArray highScores = new JSONArray();
                                JSONArray downloadedHighScores = jsonObject.getJSONArray("highScores");
                                for (int i = 0; i < downloadedHighScores.length(); i++) {
                                    JSONObject highScoreObject = downloadedHighScores.getJSONObject(i);
                                    final int id = highScoreObject.getInt("id");
                                    final int score = highScoreObject.getInt("highScore");
                                    JSONObject newHighScoreObject = new JSONObject();
                                    newHighScoreObject.put("id", id);
                                    newHighScoreObject.put("score", score);
                                    highScores.put(newHighScoreObject);
                                }
                                HighScore.clear();
                                HighScore.load(highScores);

                                SavedTime.load(jsonObject);

                                final int silver = jsonObject.getInt("silver");
                                final int gold = jsonObject.getInt("gold");

                                JSONObject currency = new JSONObject();
                                currency.put("currencyOther", silver);
                                currency.put("currencyAlpaca", gold);
                                CurrencyManager.clear();
                                CurrencyManager.load(currency);

                                final int stamina = jsonObject.getInt("stamina");
                                final int maxStamina = jsonObject.getInt("maxStamina");
                                final int firstStaminaUseTime = jsonObject.getInt("firstStaminaUseTime");

                                JSONObject staminaObject = new JSONObject();
                                staminaObject.put("currentStamina", stamina);
                                staminaObject.put("maxValue", maxStamina);
                                staminaObject.put("firstStaminaUsedTime", firstStaminaUseTime);

                                StaminaManager.clear();
                                StaminaManager.load(staminaObject);

                                SaveDataUtils.save(parent, false);

                            }
                        } catch (JSONException e) {
                            Log.e(getClass().getName(), e.getMessage(), e);
                        }
                    }
                }
        ).start();
    }

}
