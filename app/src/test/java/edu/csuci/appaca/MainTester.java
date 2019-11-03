package edu.csuci.appaca;

import org.junit.*;

import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.FoodDepletion;
import edu.csuci.appaca.data.HappinessCalc;
import edu.csuci.appaca.utils.TimeUtils;

import static org.junit.Assert.*;

public class MainTester {

    @Test
    public void alpacaWithFullFoodAfterNoTime() {
        Alpaca alpaca = Alpaca.newAlpaca(1, "");
        double expected = Alpaca.MAX_STAT;
        long currentTime = TimeUtils.getCurrentTime();
        double actual = FoodDepletion.foodDepletion(alpaca, currentTime);
        assertEquals(expected, actual, 1e-9);
    }

    @Test
    public void alpacaWithFullFoodAfterFourHoursNotFull() {
        Alpaca alpaca = Alpaca.newAlpaca(1, "");
        long previousTime = TimeUtils.getCurrentTime() - (4 * 60 * 60);
        double actual = FoodDepletion.foodDepletion(alpaca, previousTime);
        // The actual value should be less than the max stat
        assertTrue(Double.compare(actual, Alpaca.MAX_STAT) < 0);
    }

    @Test
    public void alpacaWithFullFoodAfterFourHoursNotEmpty() {
        Alpaca alpaca = Alpaca.newAlpaca(1, "");
        long previousTime = TimeUtils.getCurrentTime() - (4 * 60 * 60);
        double actual = FoodDepletion.foodDepletion(alpaca, previousTime);
        // The actual value should be greater than the min stat
        assertTrue(Double.compare(actual, Alpaca.MIN_STAT) > 0);
    }

    @Test
    public void alpacaWithFullFoodStatAfterLongTimeNotFull() {
        Alpaca alpaca = Alpaca.newAlpaca(1, "");
        double actual = FoodDepletion.foodDepletion(alpaca, 0);
        // Should definitely have less than the max stat at this point
        assertTrue(Double.compare(actual, Alpaca.MAX_STAT) < 0);
    }

    @Test
    public void alpacaWithFullFoodStatAfterLongTimeEmpty() {
        Alpaca alpaca = Alpaca.newAlpaca(1, "");
        double actual = Math.max(Alpaca.MIN_STAT, FoodDepletion.foodDepletion(alpaca, 0));
        // If clamping to min stat, should have exactly min stat at this point
        assertEquals(Alpaca.MIN_STAT, actual, 1e-9);
    }

    @Test
    public void alpacaWithFullFoodStat8HoursEmpty() {
        Alpaca alpaca = Alpaca.newAlpaca(1, "");
        long previousTime = TimeUtils.getCurrentTime() - (8 * 60 * 60);
        double actual = Math.max(Alpaca.MIN_STAT, FoodDepletion.foodDepletion(alpaca, previousTime));
        assertEquals(Alpaca.MIN_STAT, actual, 1e-9);
    }

    @Test
    public void alpacaWithFullHappinessAfterNoTime() {
        Alpaca alpaca = Alpaca.newAlpaca(1, "");
        double expected = Alpaca.MAX_STAT;
        long currentTime = TimeUtils.getCurrentTime();
        double actual = HappinessCalc.calcHappiness(null, alpaca, currentTime);
        assertEquals(expected, actual, 1e-9);
    }

    @Test
    public void alpacaWithFullHappinessAfterFourHoursNotFull() {
        Alpaca alpaca = Alpaca.newAlpaca(1, "");
        long previousTime = TimeUtils.getCurrentTime() - (4 * 60 * 60);
        double actual = HappinessCalc.calcHappiness(null, alpaca, previousTime);
        // The actual value should be less than the max stat
        assertTrue(Double.compare(actual, Alpaca.MAX_STAT) < 0);
    }

    @Test
    public void alpacaWithFullHappinessAfterFourHoursNotEmpty() {
        Alpaca alpaca = Alpaca.newAlpaca(1, "");
        long previousTime = TimeUtils.getCurrentTime() - (4 * 60 * 60);
        double actual = HappinessCalc.calcHappiness(null, alpaca, previousTime);
        // The actual value should be greater than the min stat
        assertTrue(Double.compare(actual, Alpaca.MIN_STAT) > 0);
    }

    @Test
    public void alpacaWithFullHappinessStatAfterLongTimeNotFull() {
        Alpaca alpaca = Alpaca.newAlpaca(1, "");
        double actual = HappinessCalc.calcHappiness(null, alpaca, 0);
        // Should definitely have less than the max stat at this point
        assertTrue(Double.compare(actual, Alpaca.MAX_STAT) < 0);
    }

    @Test
    public void alpacaWithFullHappinessStatAfterLongTimeEmpty() {
        Alpaca alpaca = Alpaca.newAlpaca(1, "");
        double actual = Math.max(Alpaca.MIN_STAT, HappinessCalc.calcHappiness(null, alpaca, 0));
        // If clamping to min stat, should have exactly min stat at this point
        assertEquals(Alpaca.MIN_STAT, actual, 1e-9);
    }
}
