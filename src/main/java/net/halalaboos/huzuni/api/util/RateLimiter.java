package net.halalaboos.huzuni.api.util;

import java.util.concurrent.TimeUnit;

/**
 * Used to limit the rate at which some block of code will be invoked. <br/>
 * Created by Brandon Williams on 2/22/2017.
 */
public class RateLimiter {

    private long lastTime = Timer.getSystemTime();

    private long rate;

    public RateLimiter(TimeUnit timeUnit, long rate) {
        this.rate = timeUnit.toMillis(1L) / rate;
    }

    /**
     * @return True if this rate limiter should allow whatever it is limiting.
     * */
    public boolean reached() {
        if (Timer.getSystemTime() - lastTime >= rate) {
            lastTime += Math.min(Timer.getSystemTime() - lastTime, rate);
            return true;
        }
        return false;
    }

    /**
     * Resets the time of this rate limiter.
     * */
    public void reset() {
        this.lastTime = Timer.getSystemTime();
    }

    /**
     * Set the rate at which this limiter should allow whatever it is limiting.
     * */
    public void setRate(TimeUnit timeUnit, long rate) {
        this.rate = timeUnit.toMillis(1L) / rate;
    }
}
