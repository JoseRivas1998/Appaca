package edu.csuci.appaca.data;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import edu.csuci.appaca.utils.ListUtils;

public class SaveDataUtils {

    private static final String FILENAME = "appacaSaveData.json";

    public static void load(Context context) {
        try (FileInputStream fis = context.openFileInput(FILENAME);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)) {
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            JSONObject jsonObject = new JSONObject(sb.toString());
            AlpacaFarm.load(jsonObject);
            SavedTime.load(jsonObject);
            if (jsonObject.has("currency")) {
                JSONObject currency = jsonObject.getJSONObject("currency");
                CurrencyManager.load(currency);
            } else {
                CurrencyManager.init();
            }
            if (jsonObject.has("stamina")) {
                JSONObject stamina = jsonObject.getJSONObject("stamina");
                StaminaManager.load(stamina);
            } else {
                StaminaManager.init();
            }
            if (jsonObject.has("inventory")) {
                JSONObject inventory = jsonObject.getJSONObject("inventory");
                Inventory.load(inventory);
            } else {
                Inventory.init();
            }
            if (jsonObject.has("high_scores")) {
                JSONArray highScores = jsonObject.getJSONArray("high_scores");
                HighScore.load(highScores);
            } else {
                HighScore.init();
            }
        } catch (FileNotFoundException e) {
            Log.i(SaveDataUtils.class.getName(), "No save data yet!");
            Log.e(SaveDataUtils.class.getName(), e.getMessage(), e);
            initialize();
        } catch (IOException | JSONException e) {
            Log.e(SaveDataUtils.class.getName(), e.getMessage(), e);
        }
    }

    private static void initialize() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("alpacas", new JSONArray());
            AlpacaFarm.load(jsonObject);
            SavedTime.setToNow();
            CurrencyManager.init();
            StaminaManager.init();
            Inventory.init();
            HighScore.init();
        } catch (JSONException e) {
            Log.e(SaveDataUtils.class.getName(), e.getMessage());
        }
    }

    public static void save(Context context) {
        JSONObject saveData = new JSONObject();
        try {
            JSONArray alpacas = AlpacaFarm.toJSONArray();
            saveData.put("alpacas", alpacas);
            SavedTime.setToNow();
            saveData.put("savedTime", SavedTime.lastSavedTime());
            JSONObject currency = CurrencyManager.toJSON();
            saveData.put("currency", currency);
            JSONObject stamina = StaminaManager.toJSONObject();
            saveData.put("stamina", stamina);
            JSONObject inventory = Inventory.toJSON();
            saveData.put("inventory", inventory);
            JSONArray highScores = HighScore.toJSONArray();
            saveData.put("high_scores", highScores);
        } catch (JSONException e) {
            Log.e(SaveDataUtils.class.getName(), e.getMessage(), e);
        }
        try (FileOutputStream fo = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
             OutputStreamWriter osw = new OutputStreamWriter(fo)) {
            osw.write(saveData.toString());
        } catch (IOException e) {
            Log.e(SaveDataUtils.class.getName(), e.getMessage(), e);
        }
    }

    public static void updateValuesAndSave(Context context) {
        AlpacaFarm.forEach(new ListUtils.Consumer<Alpaca>() {
            @Override
            public void accept(Alpaca alpaca) {
                alpaca.updateValuesBasedOnTime();
            }
        });
        SaveDataUtils.save(context);
    }

}
