package edu.csuci.appaca.utils;

public class MathFunctions {

    public static double clamp(double x, double min, double max) {
        return Math.max(min, Math.min(x, max));
    }

}
