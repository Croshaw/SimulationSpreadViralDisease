package me.croshaw.simulationspreadviraldisease.simulation.core.util;

import java.util.ArrayList;
import java.util.Random;

public final class ProbabilityCollection<T> {
    private final ArrayList<Pair<Float, T>> items;

    public ProbabilityCollection() {
        items = new ArrayList<>();
    }
    public boolean contains(T item) {
        return items.stream().anyMatch(pair -> pair.second().equals(item));
    }
    public void add(T item, float chance) {
        if(chance <= 0 || chance > 1)
            throw new IllegalArgumentException();
        if(contains(item))
            remove(item);
        items.add(new Pair<>(chance, item));
    }
    public float remove(T item) {
        for (int i = 0; i < items.size(); i++) {
            var curItem = items.get(i);
            if(curItem.second() == item) {
                items.remove(i);
                return curItem.first();
            }
        }
        return 0;
    }
    public float get(T item) {
        for (Pair<Float, T> pair : items) {
            if (pair.second().equals(item)) {
                return pair.first();
            }
        }
        return 0;
    }
    public T getItemByRandom(Random random) {
        float result = random.nextFloat();
        float temp = 0;
        for(var item : items) {
            if(result >= temp && result <= temp+item.first())
                return item.second();
            temp += item.first();
        }
        return null;
    }
}
