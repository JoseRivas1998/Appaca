package edu.csuci.appaca.utils;

import android.content.Context;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.Alpaca;

public class ShearUtils {

    public static int getShearValue(Alpaca alpaca, Context context) {
        int timeToMaxWool = context.getResources().getInteger(R.integer.time_to_full_wool);
        int moneyForMaxWool = context.getResources().getInteger(R.integer.money_for_full_shear);

        long timeSinceLastShear = TimeUtils.getCurrentTime() - alpaca.getLastShearTime();
        timeSinceLastShear = Math.max(0, Math.min(timeToMaxWool, timeSinceLastShear));


        return (int) (moneyForMaxWool * ((double) timeSinceLastShear / timeToMaxWool));
    }

}
