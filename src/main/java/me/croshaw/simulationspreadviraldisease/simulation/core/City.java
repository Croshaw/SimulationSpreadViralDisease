package me.croshaw.simulationspreadviraldisease.simulation.core;

import me.croshaw.simulationspreadviraldisease.simulation.core.time.WorldTime;

import java.util.Arrays;

public final class City {
    private final WorldTime time;
    private final CityType type;
    private final People[] population;
    private final float cityTypeVirusRate;
    private final float urbanTransportSaturation;
    private final float intercityTransportSaturation;

    private People[] getPeoplesByStates(PeopleState... states) {
        return Arrays.stream(population).filter(people -> Arrays.stream(states).anyMatch(state -> state == people.getState())).toArray(People[]::new);
    }
    private float getVirusRate() {
        return (cityTypeVirusRate + urbanTransportSaturation + intercityTransportSaturation
                + getPercentageOfSick() + (1 - getPercentageOfVaccinate())
                + (1 - getPercentageOfImmunity()) + time.getVirusRate() ) / 7;
    }
    private int getCountOfSick() {
        return (int)(population.length * getVirusRate());
    }

    public City(WorldTime time, CityType type, int populationCount, float cityTypeVirusRate, float urbanTransportSaturation, float intercityTransportSaturation) {
        this.type = type;
        this.time = time;
        population = new People[populationCount];
        for(int i = 0; i < populationCount; i++) {
            population[i] = new People();
        }
        this.cityTypeVirusRate = cityTypeVirusRate;
        this.urbanTransportSaturation = urbanTransportSaturation;
        this.intercityTransportSaturation = intercityTransportSaturation;
    }
    public City(WorldTime time, CityType type, People[] population, float cityTypeVirusRate, float urbanTransportSaturation, float intercityTransportSaturation) {
        this.type = type;
        this.time = time;
        this.population = population;
        this.cityTypeVirusRate = cityTypeVirusRate;
        this.urbanTransportSaturation = urbanTransportSaturation;
        this.intercityTransportSaturation = intercityTransportSaturation;
    }

    public CityType getType() {
        return type;
    }
    public People[] getPopulation() {
        return population;
    }

    public People[] getHealthyPeoples() {
        return getPeoplesByStates(PeopleState.HEALTHY);
    }
    public People[] getSickPeoples() {
        return getPeoplesByStates(PeopleState.SICK);
    }
    public People[] getVaccinatePeoples() {
        return getPeoplesByStates(PeopleState.VACCINATED);
    }
    public People[] getImmunityPeoples() {
        return getPeoplesByStates(PeopleState.IMMUNE);
    }

    public float getPercentageOfHealthy() {
        return (float) getHealthyPeoples().length / population.length;
    }
    public float getPercentageOfSick() {
        return (float) getSickPeoples().length / population.length;
    }
    public float getPercentageOfVaccinate() {
        return (float) getVaccinatePeoples().length / population.length;
    }
    public float getPercentageOfImmunity() {
        return (float) getImmunityPeoples().length / population.length;
    }
}
