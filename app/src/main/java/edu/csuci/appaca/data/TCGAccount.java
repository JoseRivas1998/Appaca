package edu.csuci.appaca.data;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class TCGAccount {

    private enum AccountData {
        INSTANCE;
        boolean loggedIn;
        String username;
        String profileImg;
        AccountData() {
            loggedIn = false;
        }
    }

    private TCGAccount() {

    }

    public static boolean isLoggedIn() {
        return AccountData.INSTANCE.loggedIn;
    }

    public static void clear() {
        AccountData.INSTANCE.loggedIn = false;
        AccountData.INSTANCE.username = null;
        AccountData.INSTANCE.profileImg = null;
    }

    public static void setAccountData(JSONObject user) {
        AccountData.INSTANCE.loggedIn = true;
        try {
            AccountData.INSTANCE.username = user.getString("username");
            if(user.get("profileImg").equals(JSONObject.NULL)) {
                AccountData.INSTANCE.profileImg = "https://tinycountrygames.com/images/content/users/no-profile-img.png";
            } else {
                AccountData.INSTANCE.profileImg = "https://tinycountrygames.com/images/content/users/uploads/" + user.getString("profileImg");
            }
        } catch (JSONException e) {
            Log.e(TCGAccount.class.getName(), e.getMessage(), e);
        }
    }

    public static String getUsername() {
        return AccountData.INSTANCE.username;
    }

    public static String getProfileImage() {
        return AccountData.INSTANCE.profileImg;
    }

}
