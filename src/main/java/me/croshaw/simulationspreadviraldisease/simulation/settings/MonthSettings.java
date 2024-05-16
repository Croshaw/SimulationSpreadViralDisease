package me.croshaw.simulationspreadviraldisease.simulation.settings;

import me.croshaw.simulationspreadviraldisease.simulation.core.CityType;

import java.time.Month;
import java.util.HashMap;

public final class MonthSettings {
    private final HashMap<Month, Float> virusRate;
    public MonthSettings() {
        virusRate = new HashMap<>();
        virusRate.put(Month.SEPTEMBER, 0.15f);
        virusRate.put(Month.OCTOBER, 0.2f);
        virusRate.put(Month.NOVEMBER, 0.25f);
        virusRate.put(Month.DECEMBER, 0.3f);
        virusRate.put(Month.JANUARY, 0.35f);
        virusRate.put(Month.FEBRUARY, 0.4f);
        virusRate.put(Month.MARCH, 0.3f);
        virusRate.put(Month.APRIL, 0.25f);
        virusRate.put(Month.MAY, 0.20f);
        virusRate.put(Month.JUNE, 0.15f);
        virusRate.put(Month.JULY, 0.1f);
    }
    public void changeVirusRate(Month month, float newValue) {
        virusRate.put(month, newValue);
    }
    public float getVirusRate(Month month) {
        return virusRate.get(month);

    }
}
