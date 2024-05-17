package me.croshaw.simulationspreadviraldisease.simulation.settings;

import me.croshaw.simulationspreadviraldisease.simulation.core.util.ProbabilityCollection;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class PeopleStateSettings {
    private final ProbabilityCollection<Duration> sickDuration;
    private final ProbabilityCollection<Duration> vaccinateDuration;

    public PeopleStateSettings() {
        sickDuration = new ProbabilityCollection<>();
        sickDuration.add(Duration.of(2, ChronoUnit.WEEKS), 0.6f);
        sickDuration.add(Duration.of(3, ChronoUnit.WEEKS), 0.15f);
        sickDuration.add(Duration.of(1, ChronoUnit.WEEKS), 0.25f);
        vaccinateDuration = new ProbabilityCollection<>();
        sickDuration.add(Duration.of(2, ChronoUnit.WEEKS), 1f);
    }

    public ProbabilityCollection<Duration> getVaccinateDuration() {
        return vaccinateDuration;
    }

    public ProbabilityCollection<Duration> getSickDuration() {
        return sickDuration;
    }
}
