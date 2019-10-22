package edu.csuci.appaca.data;

import edu.csuci.appaca.utils.TimeUtils;

public class FoodDepletion {

    /*
        Alpaca food drain is calculated based on the how much food the alpaca currently has:
        Normal rate at 100% - 75%, stage 1
        If less than 75%, the rate is doubled, stage 2
        If less than 50%, the rate is tripled, stage 3
        If less than 25%, the rate is quadrupled, stage 4
     */
    public static double foodDepletion(Alpaca alpaca, long previousTime) {
        final int TIME_TIL_FULLY_DEPLETED = (8 * 60) / 4; //8 hours * 60 minutes over 4 stages

        double alpacaFood = alpaca.getFoodStat();
        long currentTime = TimeUtils.getCurrentTime();
        long timeInMinutes = (currentTime - previousTime) * 60; //get difference from current and previous timestamp and convert to minutes
        double percentFood = (alpacaFood / Alpaca.MAX_STAT);
        int stageModifier = 4; //modifier for stage 1, 4 times slower

        if (percentFood < 0.75 && percentFood >= 0.5) {
            stageModifier = 3;
        } //stage 2
        else if (percentFood < 0.5 && percentFood >= 0.25) {
            stageModifier = 2;
        } //stage 3
        else {
            stageModifier = 1;
        } //stage 4, max speed

        //return the new food loss based on time, max hunger, time til fully depleted, and percent of
        return (alpacaFood - (timeInMinutes / (Alpaca.MAX_STAT / (TIME_TIL_FULLY_DEPLETED * stageModifier))));
    }
}
