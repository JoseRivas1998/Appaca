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

import edu.csuci.appaca.utils.TimeUtils;

import static edu.csuci.appaca.utils.TimeUtils.getCurrentTime;


public class StaminaManager {

    private enum StaminaInstance {
        INSTANCE;
        int currentValue;
        int maxValue;
        boolean loaded;
        long firstStaminaUsedTime;

        StaminaInstance() {
            this.currentValue = 0;
            this.maxValue = 4;
            this.loaded = false;
            this.firstStaminaUsedTime = getCurrentTime(); //idk why thats not working lol
        }

    }

    private static final String FILENAME = "staminaSaveData.json";

    public static int increaseMaxStamina() {

        int ret = StaminaInstance.INSTANCE.currentValue + 1;

        return ret;
    }

    public static JSONObject toJSONObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("currentStamina", StaminaInstance.INSTANCE.currentValue);
        jsonObject.put("maxValue", StaminaInstance.INSTANCE.maxValue);
        jsonObject.put("loaded", StaminaInstance.INSTANCE.loaded);

        return jsonObject;
    }

    public static boolean load(JSONObject jsonObject) throws JSONException {
        if (StaminaManager.StaminaInstance.INSTANCE.loaded) return true;

        if (!jsonObject.has("stamina")) throw new JSONException("Stamina is missing!");
//
//        JSONArray alpacas = jsonObject.getJSONArray("alpacas");
//        for (int i = 0; i < alpacas.length(); i++) {
//        }
//        StaminaInstance.INSTANCE.currentValue = increaseMaxStamina(alpacas);

        StaminaInstance.INSTANCE.loaded = true;

        return StaminaInstance.INSTANCE.loaded;
    }

}
