package edu.csuci.appaca.data;

public class FoodDepletion extends Thread
{
    public double foodDepletion(double currentFood)
    {
        //Assume that this function is called every second
        final int NUM_HOURS_TIL_DEPLETED = 4; //assuming it takes 4 hours til the hunger is fully depleted
        final double MAX_FOOD = 1.0; //assume that the max hunger of any alpaca is 1.0
        final int MINPERHOUR = 60;
        final int SECPERMIN = 60;
        final double DECREMENTVAL = (MAX_FOOD/(NUM_HOURS_TIL_DEPLETED * MINPERHOUR * SECPERMIN));

        //return the new food value after loss
        return (currentFood - DECREMENTVAL);
    }
}
