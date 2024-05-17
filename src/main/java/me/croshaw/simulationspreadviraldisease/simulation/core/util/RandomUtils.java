package me.croshaw.simulationspreadviraldisease.simulation.core.util;

import java.util.HashSet;
import java.util.Random;

public final class RandomUtils {
    public static <T> T getRandomValue(T[] array, Random random) {
        if (array == null || array.length == 0) {
            return null;
        }
        int randomIndex = random.nextInt(array.length);
        return array[randomIndex];
    }
    public static <T> HashSet<T> getRandomUniqValues(T[] array, int count, Random random) {
        if (array == null || array.length == 0) {
            return null;
        }
        if (count > array.length) {
            throw new IllegalArgumentException("Count should not exceed the length of the input array");
        }
        HashSet<T> items = new HashSet<>();
        for(int i = 0; i < count; i++) {
            if(!items.add(getRandomValue(array, random)))
                i--;
        }
        return items;
    }
    public static <T extends Number> T getRandomValueInRange(Range<T> range, Random random) {
        double dif = range.to().doubleValue() - range.from().doubleValue();
        double randomValue = random.nextDouble() * dif + range.from().doubleValue();

        if (range.getType() == Integer.class) {
            return (T) Integer.valueOf((int) Math.round(randomValue));
        } else if (range.getType() == Double.class) {
            return (T) Double.valueOf(randomValue);
        } else if (range.getType() == Long.class) {
            return (T) Long.valueOf(Math.round(randomValue));
        } else {
            throw new IllegalArgumentException("Unsupported number type.");
        }
    }

}
