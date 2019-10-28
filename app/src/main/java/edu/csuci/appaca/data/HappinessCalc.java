package edu.csuci.appaca.data;

import java.sql.Time;

import edu.csuci.appaca.data.statics.StaticClothesItem;
import edu.csuci.appaca.utils.TimeUtils;

public class HappinessCalc {
    /**
     * Calculate happiness depletion based on time, max happiness, value of clothes, and current happiness
     * @param clothes clothes that the alpaca is currently wearing, used to get value of clothes
     * @param alpaca current alpaca, used to get current happiness
     * @param previousTime time since this function was last called
     * @return new happiness value
     */
    static public double calcHappiness(StaticClothesItem clothes, Alpaca alpaca, long previousTime)
    {
        final double TIME_TIL_FULLY_DEPLETED = 8 * 60; //8 hours * 60 minutes

        double currentHappiness = alpaca.getHappinessStat();
        double percentHappiness = currentHappiness/Alpaca.MAX_STAT;

        long currentTime = TimeUtils.getCurrentTime();
        double timeInMinutes = TimeUtils.secondsToMinutes(currentTime - previousTime);

        // TODO fix this later
        double clothesValue = clothes != null ? clothes.value : 1.0;

        return (currentHappiness - (clothesValue * timeInMinutes * percentHappiness * (Alpaca.MAX_STAT / TIME_TIL_FULLY_DEPLETED))); //stub
    }
}
