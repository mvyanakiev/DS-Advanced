package core;

import models.Planet;
import models.Star;

import java.util.*;
import java.util.stream.Collectors;

public class MilkyWay implements Galaxy {
    private Map<Integer, Star> stars;
    private Map<Integer, Planet> planets;
    private Map<Star, List<Planet>> all;

    public MilkyWay() {
        this.all = new HashMap<>();
        this.planets = new HashMap<>();
        this.stars = new LinkedHashMap<>();
    }

    @Override
    public void add(Star star) {
        if (this.stars.containsKey(star.getId())) {
            throw new IllegalArgumentException();
        }

        this.stars.put(star.getId(), star);
        this.all.put(star, new ArrayList<>());
    }

    @Override
    public void add(Planet planet, Star star) {
        if (!this.stars.containsKey(star.getId()) || this.planets.containsKey(planet.getId())) {
            throw new IllegalArgumentException();
        }

        this.stars.put(star.getId(), star);
        this.planets.put(planet.getId(), planet);
        this.all.get(star).add(planet);
    }

    @Override
    public boolean contains(Planet planet) {
        return this.planets.containsKey(planet.getId());
    }

    @Override
    public boolean contains(Star star) {
        return this.stars.containsKey(star.getId());
    }

    @Override
    public Star getStar(int id) {
        if (!this.stars.containsKey(id)) {
            throw new IllegalArgumentException();
        }

        return this.stars.get(id);
    }

    @Override
    public Planet getPlanet(int id) {
        if (!this.planets.containsKey(id)) {
            throw new IllegalArgumentException();
        }

        return this.planets.get(id);
    }

    @Override
    public Star removeStar(int id) {
        Star starToRemove = this.getStar(id);
        List<Planet> planetsToRemove = this.all.get(starToRemove);


        if (!planetsToRemove.isEmpty()) {
            for (Planet planet : planetsToRemove) {
                this.planets.remove(planet.getId());
            }
        }

        this.stars.remove(id);
        this.all.remove(starToRemove);


        return starToRemove;
    }

    @Override
    public Planet removePlanet(int id) {
        if (!this.planets.containsKey(id)) {
            throw new IllegalArgumentException();
        }

        Planet toRemove = this.planets.remove(id);

        for (List<Planet> planetList : all.values()) {

            if (planetList.contains(toRemove)) {
                planetList.remove(toRemove);
                break;
            }
        }

        return toRemove;
    }

    @Override
    public int countObjects() {
        return this.stars.size() + this.planets.size();
    }

    @Override
    public Iterable<Planet> getPlanetsByStar(Star star) {
        List<Planet> result = new ArrayList<>();

        result = this.all.get(star).stream()
                .sorted(Comparator.comparing(Planet::getDistanceFromStar)
                        .thenComparing(Planet::getMass))
                .collect(Collectors.toList());


        if (result.isEmpty()) {
            return new ArrayList<>();
        } else {
            return result;
        }
    }

    @Override
    public Iterable<Star> getStars() {
        List<Star> result = new ArrayList<>();

        result = this.stars.values().stream()
                .sorted(Comparator.comparing(Star::getLuminosity).reversed())
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            return new ArrayList<>();
        } else {
            return result;
        }
    }

    @Override
    public Iterable<Star> getStarsInOrderOfDiscovery() {
        List<Star> result = new ArrayList<>();

        result = new ArrayList<>(this.stars.values());

        if (result.isEmpty()) {
            return new ArrayList<>();
        } else {
            return result;
        }
    }

    @Override
    public Iterable<Planet> getAllPlanetsByMass() {
        List<Planet> result = new ArrayList<>();

        result = this.planets.values().stream()
                .sorted(Comparator.comparing(Planet::getMass).reversed())
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            return new ArrayList<>();
        } else {
            return result;
        }
    }

    @Override
    public Iterable<Planet> getAllPlanetsByDistanceFromStar(Star star) {
        List<Planet> result = new ArrayList<>();


        result = this.all.get(star).stream()
                .sorted(Comparator.comparing(Planet::getDistanceFromStar))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            return new ArrayList<>();
        } else {
            return result;
        }
    }

    @Override
    public Map<Star, Set<Planet>> getStarsAndPlanetsByOrderOfStarDiscoveryAndPlanetDistanceFromStarThenByPlanetMass() {


        Map<Star, Set<Planet>> result = new TreeMap<>();

        for (Map.Entry<Star, List<Planet>> entry : all.entrySet()) {

            Set<Planet> planets1 = entry.getValue().stream().sorted(Comparator.comparing(Planet::getDistanceFromStar)
                    .thenComparing(Planet::getMass))
                    .collect(Collectors.toSet());

            result.putIfAbsent(entry.getKey(), planets1);
        }
        return result;
    }
}
