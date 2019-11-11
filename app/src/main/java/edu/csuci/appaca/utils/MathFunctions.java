package edu.csuci.appaca.utils;

import android.util.Log;

public class MathFunctions {

    public static double clamp(double x, double min, double max) {
        return Math.max(min, Math.min(x, max));
    }

    //https://math.stackexchange.com/questions/377169/calculating-a-value-inside-one-range-to-a-value-of-another-range/377174
    public static double map(double x, double fromMin, double fromMax, double toMin, double toMax) {
        double fromPercent = (x - fromMin) / (fromMax - fromMin);
        double toRange = toMax - toMin;
        return toMin + (fromPercent * toRange);
    }

}
