package me.croshaw.simulationspreadviraldisease.simulation.core;

import java.time.Duration;

public final class DurationCallback {
    private Duration duration;
    private final Callback callback;

    public DurationCallback(Duration duration, Callback callback) {
        this.duration = duration;
        this.callback = callback;
    }
    public Duration getDuration() {
        return duration;
    }
    public Callback getCallback() {
        return callback;
    }
    public long live(long secondsStep) {
        long remainder = duration.minusSeconds(secondsStep).getSeconds();
        if(remainder > 0) {
            duration = Duration.ofSeconds(remainder);
            return 0;
        }
        callback.execute();
        duration = Duration.ZERO;
        return remainder == 0 ? 0 : Math.abs(remainder);
    }
}
