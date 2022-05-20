package com.drozd.advertisementsystem.billboard.model;

import java.time.Duration;

public class Advertisement {
    private final Integer orderID;
    private final String text;
    private Duration duration;

    public Advertisement(Integer orderID, String text, Duration duration) {
        this.orderID = orderID;
        this.text = text;
        this.duration = duration;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public String getText() {
        return text;
    }

    public Duration getDuration() {
        return duration;
    }

    /**
     * Decreases Advertisement duration by given durationToSubtract.
     * @param durationToSubtract duration to subtract from Advertisement duration.
     * @return true if Advertisement duration after subtraction is more than zero, false when Advertisement duration after subtraction is less or equals zero.
     */
    public boolean reduceDuration(Duration durationToSubtract) throws IllegalArgumentException{
        try {
            if(durationToSubtract == null) throw new IllegalArgumentException("Advertisement.reduceDuration(): durationToSubtract cannot be null.");
            if(durationToSubtract.isNegative()) throw new IllegalArgumentException("Advertisement.reduceDuration(): durationToSubtract cannot be negative. Given duration (seconds) " + durationToSubtract.getSeconds());
            duration = duration.minus(durationToSubtract);
            return !duration.isNegative() && !duration.isZero();
        }catch (ArithmeticException e){
            e.printStackTrace();
            throw e;
        }
    }
}
