package edu.csuci.appaca.data;

import edu.csuci.appaca.utils.TimeUtils;

public class FoodDepletion {

    /*
        Alpaca food drain is calculated based on the how much food the alpaca currently has, what is the max food, and
     */
    public static double foodDepletion(Alpaca alpaca, long previousTime) {
        final int TIME_TIL_FULLY_DEPLETED = 8 * 60; //8 hours * 60 minutes over 4 stages

        double alpacaFood = alpaca.getFoodStat();
        long currentTime = TimeUtils.getCurrentTime();
        long timeInMinutes = (currentTime - previousTime) / 60; //get difference from current and previous timestamp and convert to minutes
        double percentFood = (alpacaFood / Alpaca.MAX_STAT);

        return (alpacaFood - (timeInMinutes * (1/percentFood) * Alpaca.MAX_STAT / (TIME_TIL_FULLY_DEPLETED) ) );
    }
}
