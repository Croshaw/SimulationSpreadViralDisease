package me.croshaw.simulationspreadviraldisease.simulation.core.util;

public record Range<T extends Number>(T from, T to) {
    public boolean isInRange(T number) {
        return number.doubleValue() >= from.doubleValue() && number.doubleValue() <= to.doubleValue();
    }
}
