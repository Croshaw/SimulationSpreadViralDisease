package me.croshaw.simulationspreadviraldisease.simulation.core;

import java.util.Arrays;

public final class City {
    private final CityType type;
    private final People[] population;
    private float saturationOfInCityTransport;
    private float saturationOfOutCityTransport;

    private People[] getPeoplesByStates(PeopleState... states) {
        return Arrays.stream(population).filter(people -> Arrays.stream(states).anyMatch(state -> state == people.getState())).toArray(People[]::new);
    }

    public City(CityType type, int populationCount) {
        this.type = type;
        population = new People[populationCount];
        for(int i = 0; i < populationCount; i++) {
            population[i] = new People();
        }
    }
    public City(CityType type, People[] population) {
        this.type = type;
        this.population = population;
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
