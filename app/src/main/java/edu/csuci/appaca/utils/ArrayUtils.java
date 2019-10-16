package edu.csuci.appaca.utils;

public class ArrayUtils {

    /**
     * Searches an array for a non null value and returns the first one
     * @param a The array to search
     * @param <T> The type of the array
     * @return The first non null value, or null if none was found
     */
    public static <T> T getFirstNonNull(T[] a) {
        T t = null;
        boolean found = false;
        for(int i = 0; i < a.length && !found; ++i) {
            if(a[i] != null) {
                t = a[i];
                found = true;
            }
        }
        return t;
    }

}
