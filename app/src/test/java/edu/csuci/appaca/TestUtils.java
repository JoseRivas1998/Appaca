package edu.csuci.appaca;

import org.junit.*;

import edu.csuci.appaca.utils.MathFunctions;

import static org.junit.Assert.*;

public class TestUtils {

    @Test
    public void valueWithinRangeReturnsSameValue() {
        double min = 0;
        double max = 100;
        for(double x = min; x < max; x++) {
            double expected = MathFunctions.clamp(x, min, max);
            assertEquals(expected, x, 1e-9);
        }
    }

    @Test
    public void valueEqualsMinReturnsMin() {
        double min = 0;
        double max = 100;
        double actual = MathFunctions.clamp(min, min, max);
        assertEquals(min, actual, 1e-9);
    }

    @Test
    public void valueLessMinReturnsMin() {
        double min = 0;
        double max = 100;
        double actual = MathFunctions.clamp(-1, min, max);
        assertEquals(min, actual, 1e-9);
    }

    @Test
    public void valueGreaterMaxReturnsMax() {
        double min = 0;
        double max = 100;
        double actual = MathFunctions.clamp(101, min, max);
        assertEquals(max, actual,1e-9);
    }

    @Test
    public void valueEqualsMaxReturnsMax() {
        double min = 0;
        double max = 100;
        double actual = MathFunctions.clamp(max, min, max);
        assertEquals(max, actual,1e-9);
    }

}
