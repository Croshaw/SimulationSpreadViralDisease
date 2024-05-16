package me.croshaw.simulationspreadviraldisease.simulation.core.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

public final class WorldTime {
    private LocalDateTime dateTime;
    public WorldTime() {
        this(LocalDateTime.of(0, 1, 1, 0,0,0));
    }
    public WorldTime(LocalDateTime startDate) {
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
}
