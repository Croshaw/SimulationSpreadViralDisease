package me.croshaw.simulationspreadviraldisease.simulation.settings;

import me.croshaw.simulationspreadviraldisease.simulation.core.CityType;
import me.croshaw.simulationspreadviraldisease.simulation.core.util.Range;

import java.util.HashMap;

public final class CityTypeSettings {
    private final HashMap<CityType, Float> virusRate;
    private final HashMap<CityType, Range<Integer>> populationRange;

    public CityTypeSettings() {
        virusRate = new HashMap<>() {{
            put(CityType.SMALL, 0.05f);
            put(CityType.MEDIUM, 0.15f);
            put(CityType.LARGE, 0.20f);
        }};
        populationRange = new HashMap<>() {{
            put(CityType.SMALL, new Range<>(10000, 100000));
            put(CityType.MEDIUM, new Range<>(100000, 500000));
            put(CityType.LARGE, new Range<>(500000, 1000000));
        }};
    }

    public float getVirusRate(CityType type) {
        return virusRate.get(type);
    }
    public Range<Integer> getPopulation(CityType type) {
        return populationRange.get(type);
    }

    public void changeVirusRate(CityType type, float newValue) {
        virusRate.put(type, newValue);
    }
    public void changePopulation(CityType type, int min, int max) {
        changePopulation(type, new Range<>(min, max));
    }
    public void changePopulation(CityType type, Range<Integer> range) {
        populationRange.put(type, range);
    }
}
