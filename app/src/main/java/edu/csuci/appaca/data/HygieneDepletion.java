package edu.csuci.appaca.data;

import edu.csuci.appaca.utils.TimeUtils;

public class HygieneDepletion {

    public static double hygieneDepletion(Alpaca alpaca, long previousTime) {
        // TODO see card https://trello.com/c/9S4wpJDt/110-hygiene-depletion
        final double TIME_TIL_FULLY_DEPLETED = 16 * 60; //16 hours * 60 minutes

        long currentTime = TimeUtils.getCurrentTime();
        double timeInMinutes = TimeUtils.secondsToMinutes(currentTime - previousTime);

        double alpacaClean = alpaca.getHygieneStat();
        double percentClean = alpacaClean / Alpaca.MAX_STAT;

        return alpacaClean - (timeInMinutes * percentClean * (Alpaca.MAX_STAT/TIME_TIL_FULLY_DEPLETED));
    }

}
