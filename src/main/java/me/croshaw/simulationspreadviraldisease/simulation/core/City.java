package me.croshaw.simulationspreadviraldisease.simulation.core;

import me.croshaw.simulationspreadviraldisease.simulation.core.time.WorldTime;
import me.croshaw.simulationspreadviraldisease.simulation.core.util.ProbabilityCollection;
import me.croshaw.simulationspreadviraldisease.simulation.core.util.RandomUtils;
import me.croshaw.simulationspreadviraldisease.simulation.core.util.Range;
import me.croshaw.simulationspreadviraldisease.simulation.settings.CityTypeSettings;
import me.croshaw.simulationspreadviraldisease.simulation.settings.PeopleStateSettings;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public final class City {
    public final class CityBuilder {
        private CityTypeSettings typeSettings;
        private PeopleStateSettings stateSettings;
        private Random random;
        private WorldTime worldTime;
        private CityType type;
        private int populationCount;
        private float urbanTransportSaturation;
        private float intercityTransportSaturation;

        private void fillRandomly(Random random) {
            if(type == null) {
                type = RandomUtils.getRandomValue(CityType.values(), random);
            }
            if(populationCount <= 0) {
                populationCount = RandomUtils.getRandomValueInRange(typeSettings.getPopulation(type), random);
            }
            if(urbanTransportSaturation < 0) {
                urbanTransportSaturation = RandomUtils.getRandomValueInRange(new Range<>(0, 1), random);
            }
            if(intercityTransportSaturation < 0) {
                intercityTransportSaturation = RandomUtils.getRandomValueInRange(new Range<>(0, 1), random);
            }
        }

        public CityBuilder(CityTypeSettings typeSettings, PeopleStateSettings stateSettings) {
            this.typeSettings = typeSettings;
            this.stateSettings = stateSettings;
        }
        public CityBuilder setWorldTime(WorldTime worldTime) {
            this.worldTime = worldTime;
            return this;
        }
        public CityBuilder setType(CityType type) {
            this.type = type;
            return this;
        }
        public CityBuilder setUrbanTransportSaturation(float urbanTransportSaturation) {
            this.urbanTransportSaturation = urbanTransportSaturation;
            return this;
        }
        public CityBuilder setIntercityTransportSaturation(float intercityTransportSaturation) {
            this.intercityTransportSaturation = intercityTransportSaturation;
            return this;
        }
        public CityBuilder withRandom(Random random) {
            this.random = random;
            return this;
        }
        public City build() {
            if(typeSettings == null || stateSettings == null || random == null || worldTime == null)
                throw new NullPointerException();
            fillRandomly(random);
            return new City(random, stateSettings.getSickDuration(), stateSettings.getVaccinateDuration(), worldTime, type, populationCount, typeSettings.getVirusRate(type), urbanTransportSaturation, intercityTransportSaturation);
        }
    }
    private final Random random;
    private final ProbabilityCollection<Duration> sickDuration;
    private final ProbabilityCollection<Duration> vaccinateDuration;
    private final WorldTime time;
    private final CityType type;
    private final People[] population;
    private final float cityTypeVirusRate;
    private final float urbanTransportSaturation;
    private final float intercityTransportSaturation;

    private final ArrayList<People> peopleToVaccine;

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

    private City(Random random, ProbabilityCollection<Duration> sickDuration, ProbabilityCollection<Duration> vaccinateDuration, WorldTime time, CityType type, int populationCount, float cityTypeVirusRate, float urbanTransportSaturation, float intercityTransportSaturation) {
        this.random = random;
        this.sickDuration = sickDuration;
        this.vaccinateDuration = vaccinateDuration;
        this.type = type;
        this.time = time;
        population = new People[populationCount];
        for(int i = 0; i < populationCount; i++) {
            population[i] = new People();
        }
        this.cityTypeVirusRate = cityTypeVirusRate;
        this.urbanTransportSaturation = urbanTransportSaturation;
        this.intercityTransportSaturation = intercityTransportSaturation;
        peopleToVaccine = new ArrayList<>();
    }
    private City(Random random, ProbabilityCollection<Duration> sickDuration, ProbabilityCollection<Duration> vaccinateDuration,  WorldTime time, CityType type, People[] population, float cityTypeVirusRate, float urbanTransportSaturation, float intercityTransportSaturation) {
        this.random = random;
        this.sickDuration = sickDuration;
        this.vaccinateDuration = vaccinateDuration;
        this.type = type;
        this.time = time;
        this.population = population;
        this.cityTypeVirusRate = cityTypeVirusRate;
        this.urbanTransportSaturation = urbanTransportSaturation;
        this.intercityTransportSaturation = intercityTransportSaturation;
        peopleToVaccine = new ArrayList<>();
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

    public void live(long secondsStep) {
        for(var people : peopleToVaccine)
            people.vaccinate(vaccinateDuration.getItemByRandom(random));
        peopleToVaccine.clear();
        for(var people : population)
            people.live(secondsStep);
        var uniqPeoples = RandomUtils.getRandomUniqValues(population, getCountOfSick(), random);
        for(var people : uniqPeoples) {
            people.sick(sickDuration.getItemByRandom(random));
        }
    }
    public int makeVaccine(int count) {
        var healthyPeoples = getHealthyPeoples();
        int len = healthyPeoples.length;
        if(count > len) {
            peopleToVaccine.addAll(Arrays.stream(healthyPeoples).toList());
            return count - len;
        }
        peopleToVaccine.addAll(RandomUtils.getRandomUniqValues(getHealthyPeoples(), count, random));
        return 0;
    }
}
