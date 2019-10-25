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
        double timeInMinutes = TimeUtils.secondsToMinutes(currentTime - previousTime);
        double percentFood = (alpacaFood / Alpaca.MAX_STAT);
        if (percentFood == 1.0) {
            percentFood -= 0.01; //if food is full make it so 1.0-percentfood isn't zero
        }

        return (alpacaFood - (timeInMinutes * (1.0 - percentFood) * Alpaca.MAX_STAT / (TIME_TIL_FULLY_DEPLETED)));
    }
}
