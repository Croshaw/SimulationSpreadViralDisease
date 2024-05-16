package me.croshaw.simulationspreadviraldisease.simulation.settings;

import me.croshaw.simulationspreadviraldisease.simulation.core.CityType;

import java.util.HashMap;

public class CityTypeSettings {
    private final HashMap<CityType, Float> virusRate;
    public CityTypeSettings() {
        virusRate = new HashMap<>();
        virusRate.put(CityType.SMALL, 0.05f);
        virusRate.put(CityType.MEDIUM, 0.15f);
        virusRate.put(CityType.LARGE, 0.20f);
    }
    public void changeVirusRate(CityType type, float newValue) {
        virusRate.put(type, newValue);
    }
    public float getVirusRate(CityType type) {
        return virusRate.get(type);

    }
}
