package edu.csuci.appaca.data;

import edu.csuci.appaca.utils.TimeUtils;

public class FoodDepletion {

    /*
        Alpaca food drain is calculated based on the how much food the alpaca currently has:
        If less than 75%, the rate is doubled
        If less than 50%, the rate is tripled
        If less than 25%, the rate is quadrupled
     */
    public static double foodDepletion(Alpaca alpaca, long previousTime) {
        final int TIME_TIL_FULLY_DEPLETED = 8 * 60; //8 hours * 60 minutes

        double alpacaFood = alpaca.getFoodStat();
        long currentTime = TimeUtils.getCurrentTime();
        long timeInMinutes = (currentTime - previousTime) * 60; //get difference from current and previous timestamp and convert to minutes

        //return the new food loss based on time, max hunger, and time til fully depleted
        return (alpacaFood - (timeInMinutes / (Alpaca.MAX_STAT / TIME_TIL_FULLY_DEPLETED)));
    }
}
