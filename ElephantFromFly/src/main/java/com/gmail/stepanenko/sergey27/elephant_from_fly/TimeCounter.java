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
    // Private fields.

    long mStartTimeMillis; // Start time in milliseconds.

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

    // Private methods.

    /** Default constructor. */
    private TimeCounter(){
        reset();
    }
} // class TimeCounter
