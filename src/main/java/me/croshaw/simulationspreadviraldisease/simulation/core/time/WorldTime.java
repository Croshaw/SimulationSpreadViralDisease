package me.croshaw.simulationspreadviraldisease.simulation.core.time;

import me.croshaw.simulationspreadviraldisease.simulation.settings.MonthSettings;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

public final class WorldTime {
    private LocalDateTime dateTime;
    private final MonthSettings settings;

    public WorldTime(MonthSettings settings) {
        this(settings, LocalDateTime.of(0, 1, 1, 0,0,0));
    }
    public WorldTime(MonthSettings settings, LocalDateTime startDate) {
        this.settings = settings;
        dateTime = startDate;
    }
    public void addTime(long secondsStep) {
        dateTime = dateTime.plusSeconds(secondsStep);
    }
    public Season getSeason() {
        return Season.of(dateTime.getMonth());
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }
    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }
    public Month getMonth() {
        return dateTime.getMonth();
    }
    public float getVirusRate() {
        return settings.getVirusRate(getMonth());
    }
}
