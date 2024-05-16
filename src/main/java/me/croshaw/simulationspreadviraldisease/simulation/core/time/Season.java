package me.croshaw.simulationspreadviraldisease.simulation.core.time;

import java.time.Month;

public enum Season {
    WINTER("Зима"), SPRING("Весна"), SUMMER("Лето"), AUTUMN("Осень");
    private final String name;
    Season(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public static Season of(Month month) {
        int monthValue = month.getValue();
        return switch (monthValue) {
            case 3, 4, 5 -> Season.SPRING;
            case 6, 7, 8 -> Season.SUMMER;
            case 9, 10, 11 -> Season.AUTUMN;
            default -> Season.WINTER;
        };
    }
}
