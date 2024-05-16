package me.croshaw.simulationspreadviraldisease.simulation.core;

public enum CityType {
    LARGE("Мегаполис"), MEDIUM("Город"), SMALL("Посёлок");
    private final String name;
    CityType(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
