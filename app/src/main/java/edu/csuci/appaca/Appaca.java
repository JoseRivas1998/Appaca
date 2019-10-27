package edu.csuci.appaca;

import android.app.Application;

import edu.csuci.appaca.data.SaveDataUtils;
import edu.csuci.appaca.data.statics.ShopData;

public class Appaca extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ShopData.load(getApplicationContext());
        SaveDataUtils.load(getApplicationContext());
    }
}
