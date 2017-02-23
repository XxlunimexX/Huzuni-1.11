package net.halalaboos.huzuni.api.util;

/**
 * Simple timer class, mostly used for keeping track of when st00f occured.
 */
public final class Timer {

    protected long lastCheck = getSystemTime();

    /**
     * Checks if the passed time reached the targetted time.
     */
    public boolean hasReach(int targetTime) {
        return getTimePassed() >= targetTime;
    }

    /**
     * @return The time between the current time and the last time this timer was reset.
     * */
    public long getTimePassed() {
        return getSystemTime() - lastCheck;
    }

    /**
     * Resets the time this timer was keeping track of.
     * */
    public void reset() {
        lastCheck = getSystemTime();
    }

    /**
     * @return The system time in milliseconds.
     * */
    public static long getSystemTime() {
        return System.nanoTime() / (long) (1E6);
    }
    
}