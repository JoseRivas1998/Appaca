package edu.csuci.appaca.data;

import org.json.JSONException;
import org.json.JSONObject;

public class TCGDeviceId {

    private enum UserData {
        INSTANCE;
        String deviceId;
    }

    private TCGDeviceId() {
    }

    public static void load(JSONObject jsonObject) {
        if (jsonObject == null || !jsonObject.has("deviceId")) {
            UserData.INSTANCE.deviceId = "";
            return;
        }
        String deviceId = "";
        try {
            deviceId = jsonObject.getString("deviceId");
        } catch (JSONException ignored) {
        }
        UserData.INSTANCE.deviceId = deviceId;
    }

    public static String getDeviceId() {
        return UserData.INSTANCE.deviceId;
    }

    public static void setDeviceId(String deviceId) {
        UserData.INSTANCE.deviceId = deviceId;
    }

}
