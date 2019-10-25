package edu.csuci.appaca.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {

    /*
        Gets current time as a measure of seconds since epoch
     */
    static public long getCurrentTime() {
        final int MILLISECONDS_IN_SECONDS = 1000;
        Calendar date = Calendar.getInstance();
        date.setTimeZone(TimeZone.getTimeZone("UTC")); //change to UTC
        Date clock = date.getTime(); //get current time from the UTC calendar
        long currentTime = clock.getTime(); //gets milliseconds since epoch
        currentTime /= MILLISECONDS_IN_SECONDS; //turns milliseconds to seconds
        return currentTime;
    }
}
