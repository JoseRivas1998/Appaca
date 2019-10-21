package edu.csuci.appaca.data;

import edu.csuci.appaca.utils.TimeUtils;

public class FoodDepletion {
    public double foodDepletion(Alpaca alpaca, long previousTime) {
        final double FOOD_LOSS_PER_MINUTE = 0.015; //how much food is decremented by per minute

        long currentTime = TimeUtils.getCurrentTime();

        long timeInMinutes = (currentTime - previousTime) * 60; //get difference from current and previous timestamp and convert to minutes

        double alpacaFood = alpaca.getFoodStat();

        //return the new food loss based on time and loss per minute
        return (alpacaFood - (timeInMinutes * FOOD_LOSS_PER_MINUTE));
    }
}
