package edu.csuci.appaca.data;

public class FoodDepletion extends Thread
{
    public double foodDepletion(Alpaca alpaca, long previousTime, long currentTime)
    {
        final double FOOD_LOSS_PER_MINUTE = 0.015; //how much food is decremented by per minute

        long timeInMinutes = (currentTime - previousTime) * 60; //get difference from current and previous timestamp and convert to minutes

        //return the new food loss based on time and loss per minute
        return (timeInMinutes * FOOD_LOSS_PER_MINUTE);
    }
}
