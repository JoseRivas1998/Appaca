package edu.csuci.appaca.data;

import edu.csuci.appaca.utils.TimeUtils;

public class FoodDepletion {

    /**
        Alpaca food drain is calculated based on the how much food the alpaca currently has, what is the max food, and the time since the food was last depleted
        @param alpaca current alpaca
        @param previousTime time that this function was last called in seconds
        @return the new food value for the alpaca
     */
    public static double foodDepletion(Alpaca alpaca, long previousTime) {
        final double TIME_TIL_FULLY_DEPLETED = 8 * 60; //8 hours * 60 minutes over 4 stages
        final double MAXPERCENTAGE = 1.0; //100%

        double alpacaFood = alpaca.getFoodStat();
        long currentTime = TimeUtils.getCurrentTime();
        double timeInMinutes = TimeUtils.secondsToMinutes(currentTime - previousTime);
        double percentFood = (alpacaFood / Alpaca.MAX_STAT);
        if (percentFood == MAXPERCENTAGE) { //if at 100% food
            percentFood -= 0.01; //if food is full make it so Alpaca.MAX_STAT - percentfood isn't zero
        }

        return (alpacaFood - (timeInMinutes * (MAXPERCENTAGE - percentFood) * Alpaca.MAX_STAT / (TIME_TIL_FULLY_DEPLETED)));
    }
}
