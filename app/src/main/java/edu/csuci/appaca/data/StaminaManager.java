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
            this.maxValue = 0;
            this.loaded = false;
            // just initialize it to zero
            this.firstStaminaUsedTime = getCurrentTime(); //idk why thats not working lol
        }

    }

    private static final String FILENAME = "staminaSaveData.json";

    public static int increaseMaxStamina() {
        // this whole method can be a one liner
        // StaminaInstance.INSTANCE.currentValue++
        int ret = StaminaInstance.INSTANCE.currentValue + 1;

        return ret;
    }

    public static JSONObject toJSONObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("currentStamina", StaminaInstance.INSTANCE.currentValue);
        jsonObject.put("maxValue", StaminaInstance.INSTANCE.maxValue);
        jsonObject.put("loaded", StaminaInstance.INSTANCE.loaded); // dont include this
        // include firstStaminaUsedTime

        return jsonObject;
    }

    public static boolean load(JSONObject jsonObject) throws JSONException {
        if (StaminaManager.StaminaInstance.INSTANCE.loaded) return true;

        // we are assuming that the object we are accepting  to begin with
        if (!jsonObject.has("stamina")) throw new JSONException("Stamina is missing!");
        // Here is the JSON that this method should be expecting
        /*
         * {
         *      "currentStamina": ##,
         *      "maxValue": ##,
         *      "firstStaminaUsedTime": ####
         * }
         */
        // We are simply copying the values from the json object to the enum
        // Here is a sample of grabbing a thing
        // StaminaInstance.INSTANCE.currentValue = jsonObject.getInt("currentValue");

        StaminaInstance.INSTANCE.loaded = true;

        return StaminaInstance.INSTANCE.loaded;
    }

    /*
     * We need a static method called init:
     *  if loaded is true, return
     *  set max stamina to 4 (use a static constant)
     *  set current stamina = 4
     *  set firstStaminaUsedTime to 0 (this value is only relevant when current stamina != max stamina
     *  set loaded to true
     */

}
