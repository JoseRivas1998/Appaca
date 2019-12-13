package edu.csuci.appaca;

import android.app.Application;
import android.content.Intent;

import edu.csuci.appaca.data.SaveDataUtils;
import edu.csuci.appaca.data.statics.ShopData;
import edu.csuci.appaca.notifications.NotificationService;

public class Appaca extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ShopData.load(getApplicationContext());
        SaveDataUtils.load(getApplicationContext());
    }
}
