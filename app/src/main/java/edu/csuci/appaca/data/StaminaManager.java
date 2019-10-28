package edu.csuci.appaca.data;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class StaminaManager {

    private enum StaminaInstance {
        INSTANCE;
        int currentValue;
        int maxValue;
        boolean loaded;
        List<Alpaca> alpacas;

        StaminaInstance() {
            this.currentValue = 0;
            this.maxValue = 4;
            this.loaded = false;
            this.alpacas = new ArrayList<>();
        }

    }

    private static final String FILENAME = "staminaSaveData.json";

    public static int increaseMaxStamina(JSONArray alpacas) {
        int ret;
        if (alpacas.length() == 1) {
            ret = 4;
        } else {
            ret = 4 + alpacas.length() - 1;
        }

        return ret;
    }

    public static void saveStamina(Context context) {
        JSONObject saveData = new JSONObject();
        try {
            saveData.put("currentStamina", StaminaInstance.INSTANCE.currentValue);
            SavedTime.setToNow();
            saveData.put("savedTime", SavedTime.lastSavedTime());
        } catch (JSONException e) {
            Log.e(StaminaManager.class.getName(), e.getMessage(), e);
        }
        try (FileOutputStream fo = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
             OutputStreamWriter osw = new OutputStreamWriter(fo)) {
            osw.write(saveData.toString());
        } catch (IOException e) {
            Log.e(StaminaManager.class.getName(), e.getMessage(), e);
        }
    }

    public static boolean load(JSONObject jsonObject) throws JSONException {
        if (StaminaManager.StaminaInstance.INSTANCE.loaded) return true;

        if (!jsonObject.has("stamina")) throw new JSONException("Stamina is missing!");

        JSONArray alpacas = jsonObject.getJSONArray("alpacas");
        for (int i = 0; i < alpacas.length(); i++) {
            StaminaManager.StaminaInstance.INSTANCE.alpacas.add(Alpaca.ofJSON(alpacas.getJSONObject(i)));
        }
        StaminaInstance.INSTANCE.currentValue = increaseMaxStamina(alpacas);

        StaminaInstance.INSTANCE.loaded = true;

        return StaminaInstance.INSTANCE.loaded;
    }

}
