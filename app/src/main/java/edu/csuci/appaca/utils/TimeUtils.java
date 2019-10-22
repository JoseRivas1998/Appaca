package edu.csuci.appaca.utils;

import java.util.Date;

public class TimeUtils {

    /*
        Gets current time as a measure of seconds since epoch
     */
    static public long getCurrentTime() {
        final int MILLISECONDS_IN_SECONDS = 1000;
        Date clock = new Date();
        long currentTime = clock.getTime(); //gets milliseconds since epoch
        currentTime /= MILLISECONDS_IN_SECONDS; //turns milliseconds to seconds
        return currentTime;
    }
}
