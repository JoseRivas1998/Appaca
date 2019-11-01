package edu.csuci.appaca.data;

import org.json.JSONException;
import org.json.JSONObject;

import edu.csuci.appaca.utils.TimeUtils;

public class SavedTime {

    private enum TimeInstance {
        INSTANCE;
        long lastSavedTime;
        TimeInstance() {
            lastSavedTime = 0;
        }
    }

    public static void load(JSONObject jsonObject) {
        if(jsonObject == null || !jsonObject.has("savedTime")) {
            setToNow();
            return;
        }
        try {
            TimeInstance.INSTANCE.lastSavedTime = jsonObject.getLong("savedTime");
        } catch (JSONException e) {
            setToNow();
        }
    }

    public static void setToNow() {
        TimeInstance.INSTANCE.lastSavedTime = TimeUtils.getCurrentTime();
    }

    public static long lastSavedTime() {
        return TimeInstance.INSTANCE.lastSavedTime;
    }

}
