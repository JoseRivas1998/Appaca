package edu.csuci.appaca.data;

import org.json.JSONException;
import org.json.JSONObject;

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
            this.firstStaminaUsedTime = getCurrentTime();
        }

    }

    public static final int MAX_STAMINA = 4;

    public static void increaseMaxStamina() {
        StaminaInstance.INSTANCE.currentValue++;
    }

    public static void decreaseCurrentStamina() {
        if (StaminaInstance.INSTANCE.currentValue > 0) {
            if (getCurrentStamina() == StaminaInstance.INSTANCE.maxValue)
                StaminaInstance.INSTANCE.firstStaminaUsedTime = getCurrentTime();

            StaminaInstance.INSTANCE.currentValue--;
        }
    }

    public static void increaseCurrentStaminaToMax() {
        StaminaInstance.INSTANCE.currentValue = StaminaInstance.INSTANCE.maxValue;
        StaminaInstance.INSTANCE.firstStaminaUsedTime = 0;
    }

    public static int getCurrentStamina() {
        return StaminaInstance.INSTANCE.currentValue;
    }

    public static long getFirstStaminaUsedTime() {
        return StaminaInstance.INSTANCE.firstStaminaUsedTime;
    }

    public static JSONObject toJSONObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("currentStamina", StaminaInstance.INSTANCE.currentValue);
        jsonObject.put("maxValue", StaminaInstance.INSTANCE.maxValue);
        jsonObject.put("firstStaminaUsedTime", StaminaInstance.INSTANCE.firstStaminaUsedTime);
        return jsonObject;
    }

    public static void load(JSONObject jsonObject) throws JSONException {
        if (StaminaManager.StaminaInstance.INSTANCE.loaded) return;
        try {
            // we are assuming that the object we are accepting  to begin with
            if (!jsonObject.has("currentStamina")) throw new JSONException("Stamina is missing");
            if (!jsonObject.has("maxValue")) throw new JSONException("maxValue is missing");
            if (!jsonObject.has("firstStaminaUsedTime"))
                throw new JSONException("firstStaminaUsedTime is missing");

            // StaminaInstance.INSTANCE.currentValue = jsonObject.getInt("currentValue");

            StaminaInstance.INSTANCE.currentValue = jsonObject.getInt("currenValue");
            StaminaInstance.INSTANCE.maxValue = jsonObject.getInt("maxValue");
            StaminaInstance.INSTANCE.maxValue = jsonObject.getInt("firstStaminaUsedTime");
        } catch (JSONException je) {
            init();
        }
        StaminaInstance.INSTANCE.loaded = true;
    }

    public static void init() {
        StaminaInstance.INSTANCE.maxValue = MAX_STAMINA;
        StaminaInstance.INSTANCE.currentValue = MAX_STAMINA;
        StaminaInstance.INSTANCE.firstStaminaUsedTime = 0;
    }

}
