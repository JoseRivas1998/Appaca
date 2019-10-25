package edu.csuci.appaca.data;

import java.sql.Time;

import edu.csuci.appaca.data.statics.StaticClothesItem;
import edu.csuci.appaca.utils.TimeUtils;

public class HappinessCalc {
    static public double calcHappiness(StaticClothesItem clothes, Alpaca alpaca, long previousTime)
    {
        double currentHappiness = alpaca.getHappinessStat();
        double percentHappiness = currentHappiness/Alpaca.MAX_STAT;

        long currentTime = TimeUtils.getCurrentTime();
        double timeInMinutes = TimeUtils.secondsToMinutes(currentTime - previousTime);

        return 0.0; //stub
    }
}
