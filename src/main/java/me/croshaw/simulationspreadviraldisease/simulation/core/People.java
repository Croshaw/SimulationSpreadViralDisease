package me.croshaw.simulationspreadviraldisease.simulation.core;

import java.time.Duration;

public final class People {
    private PeopleState state;
    private DurationCallback stateDuration;
    private boolean changeState(PeopleState newState, Duration durationState) {
        if(state == PeopleState.IMMUNE)
            return false;
        switch (newState) {
            case SICK -> stateDuration = new DurationCallback(durationState, () -> {
                this.state = PeopleState.HEALTHY;
                this.stateDuration = null;
            });
            case VACCINATED -> {
                if (state == PeopleState.SICK)
                    return false;
                stateDuration = new DurationCallback(durationState, () -> {
                    this.state = PeopleState.IMMUNE;
                    this.stateDuration = null;
                });
            }
            case HEALTHY -> stateDuration = null;
        }
        state = newState;
        return true;
    }
    public People() {
        state = PeopleState.HEALTHY;
        stateDuration = null;
    }
    public boolean heal() {
        return changeState(PeopleState.HEALTHY, Duration.ZERO);
    }
    public boolean vaccinate(Duration duration) {
        return changeState(PeopleState.VACCINATED, duration);
    }
    public boolean sick(Duration duration) {
        return changeState(PeopleState.SICK, duration);
    }
    public long live(long secondsStep) {
        return stateDuration == null ? 0 : stateDuration.live(secondsStep);
    }
    public PeopleState getState() {
        return state;
    }
}
