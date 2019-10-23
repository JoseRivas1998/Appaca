package edu.csuci.appaca;

import android.app.Application;

import edu.csuci.appaca.data.SaveDataUtils;

public class Appaca extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SaveDataUtils.load(getApplicationContext());
    }
}
