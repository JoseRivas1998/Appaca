package edu.csuci.appaca.utils;

import java.util.Date;

public class TimeUtils {

    /*
        Gets current time as a measure of seconds since epoch
     */
    static public long getCurrentTime() {
        Date clock = new Date();
        long currentTime = clock.getTime(); //gets milliseconds since epoch
        currentTime /= 1000; //turns milliseconds to seconds
        return currentTime;
    }
}
