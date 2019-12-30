package edu.csuci.appaca.data;

import org.json.JSONException;
import org.json.JSONObject;

public class TCGAccount {

    private enum AccountData {
        INSTANCE;
        boolean loggedIn;
        String username;
        AccountData() {
            loggedIn = false;
        }
    }

    private TCGAccount() {

    }

    public static boolean isLoggedIn() {
        return AccountData.INSTANCE.loggedIn;
    }

    public static void setAccountData(JSONObject user) {
        AccountData.INSTANCE.loggedIn = true;
        try {
            AccountData.INSTANCE.username = user.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
