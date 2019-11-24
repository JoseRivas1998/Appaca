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
        final double TIME_TIL_FULLY_DEPLETED = 0.1; //8 hours * 60 minutes over 4 stages
        final double MAXPERCENTAGE = 1.0; //100%

        double alpacaFood = alpaca.getFoodStat();
        long currentTime = TimeUtils.getCurrentTime();
        double timeInMinutes = TimeUtils.secondsToMinutes(currentTime - previousTime);
        double percentFood = (alpacaFood / Alpaca.MAX_STAT);

        return (alpacaFood - (timeInMinutes * percentFood * Alpaca.MAX_STAT / (TIME_TIL_FULLY_DEPLETED)));
    }
}
