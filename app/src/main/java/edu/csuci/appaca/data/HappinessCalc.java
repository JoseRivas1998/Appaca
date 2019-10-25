package edu.csuci.appaca.data;

import java.sql.Time;

import edu.csuci.appaca.data.statics.StaticClothesItem;
import edu.csuci.appaca.utils.TimeUtils;

public class HappinessCalc {
    static public double calcHappiness(StaticClothesItem clothes, Alpaca alpaca, long previousTime)
    {
        final double TIME_TIL_FULLY_DEPLETED = 8 * 60; //8 hours * 60 minutes
        final double MAXPERCENT = 1.0;

        double currentHappiness = alpaca.getHappinessStat();
        double percentHappiness = currentHappiness/Alpaca.MAX_STAT;

        long currentTime = TimeUtils.getCurrentTime();
        double timeInMinutes = TimeUtils.secondsToMinutes(currentTime - previousTime);

        double clothesValue = clothes.value;

        return (currentHappiness - (clothesValue * timeInMinutes * (MAXPERCENT - percentHappiness) * (Alpaca.MAX_STAT / TIME_TIL_FULLY_DEPLETED))); //stub
    }
}
