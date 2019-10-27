package edu.csuci.appaca.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;



public class StaminaManager {


    private static void initializeStamina() {
        try {
            JSONObject currentStamina = new JSONObject();
            currentStamina.put("currentStamina", 4);
            JSONObject maxStamina = new JSONObject();
            maxStamina.put("maxStamina", 4);
            SavedTime.setToNow();
        } catch (JSONException e) {
            Log.e(StaminaManager.class.getName(), e.getMessage());
        }
    }

    public static JSONObject increaseMaxStamina(JSONArray alpacas) {
        int ret;
        JSONObject retStamina = new JSONObject();

        if (alpacas.length() == 1) {
            ret = 4;
        } else {
            ret = 4 + alpacas.length() - 1;
        }
        try {
            retStamina.put("retStamina", ret);
            return retStamina;
        } catch (JSONException e) {
            Log.e(StaminaManager.class.getName(), e.getMessage());
        }
        return retStamina;
    }


}




//TODO: Loading, Saving
    //i need initialization, load, saving, and caculating the amount of stamina if the user has more than one alpaca.



/**
 * This includes saving the max stamina, the current stamina, and the time that the first stamina was used.
 **/