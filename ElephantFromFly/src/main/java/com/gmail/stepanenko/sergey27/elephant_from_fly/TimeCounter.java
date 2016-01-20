/***************************************************************************************************
 * @file TimeCounter.java
 * @author Sergey Stepanenko (sergey.stepanenko.27@gmail.com)
 * @description Contains implementation of the TimeCounter class.
 **************************************************************************************************/

package com.gmail.stepanenko.sergey27.elephant_from_fly;

import java.util.concurrent.TimeUnit;

/**
 * Time counter: counts time passed since start of counting process.
 */
public class TimeCounter {

    // Private static constants.

    // Time format: hh:mm:ss.SSS.
    private static final String TIME_FORMAT = "%02d:%02d:%02d.%03d";


    // Private fields.

    private long mStartTimeMillis; // Start time in milliseconds.

    // Public static methods.

    /** Creates a new instance of TimeCounter and starts it.
     *  @return Started time counter.
     *  */
    public static TimeCounter start(){
        return new TimeCounter();
    }

    // Public methods.

    /** Resets time counter (updates start time). */
    public void reset() {
        mStartTimeMillis = System.currentTimeMillis();
    }

    /** Gets passed time in milliseconds. */
    public long passedTimeMillis(){
        long passedTime = System.currentTimeMillis() - mStartTimeMillis;

        return passedTime;
    }

    /** Gets passed time in given time units. */
    public long passedTime(TimeUnit timeUnit) {
        assert  timeUnit != null;

        long passedTime = timeUnit.convert(passedTimeMillis(), TimeUnit.MILLISECONDS);

        return passedTime;
    }

    /** Gets passed time in the format: hh:mm:ss.SSS.
     *  @return Passed time as string.
     *  */
    public String getPassedTimeAsString(){
        long passedTimeMillis = passedTimeMillis();

        long hours = TimeUnit.MILLISECONDS.toHours(passedTimeMillis);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(passedTimeMillis - TimeUnit.HOURS.toMillis(hours));

        long seconds = TimeUnit.MILLISECONDS.toSeconds(passedTimeMillis -
                TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes));

        long milliseconds = TimeUnit.MILLISECONDS.toMillis(
                passedTimeMillis - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes) -
                        TimeUnit.SECONDS.toMillis(seconds));

        String passedTimeStr = String.format(TIME_FORMAT, hours, minutes, seconds, milliseconds);

        return passedTimeStr;
    }

    // Private methods.

    /** Default constructor. */
    private TimeCounter(){
        reset();
    }
} // class TimeCounter
