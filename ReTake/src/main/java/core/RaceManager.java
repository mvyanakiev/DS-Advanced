package core;

import models.Car;

import java.util.*;
import java.util.stream.Collectors;

public class RaceManager implements Race {
    private Set<Car> cars;
    private Map<Long, Car> byId;
    private Set<Car> byLapTime;
    private Set<Car> byHorsePower;
    private Set<Car> byLapsCount;



    public RaceManager() {
        this.cars = new TreeSet<>(Comparator.comparing(Car::getJoinedOn).thenComparing(Car::getId));
        this.byId = new HashMap<>();
        this.byLapTime = new TreeSet<>(Comparator.comparing(Car::getBestLastTime));
        this.byHorsePower = new TreeSet<>(Comparator.comparing(Car::getHorsePower).reversed());
        this.byLapsCount = new TreeSet<>(Comparator.comparing(Car::getLapsCount).reversed());
    }

    @Override
    public void add(Car car) {
        if(this.byId.containsKey(car.getId())){
            throw new UnsupportedOperationException();
        }

        this.byId.put(car.getId(), car);
        this.cars.add(car);
        this.byLapTime.add(car);
        this.byHorsePower.add(car);
        this.byLapsCount.add(car);
    }

    @Override
    public boolean contains(Car car) {
        return this.byId.containsKey(car.getId());
    }

    @Override
    public Car getCar(long id) {
        Car car = this.byId.get(id);

        if(car == null){
            throw new UnsupportedOperationException();
        }

        return car;
    }

    @Override
    public Car removeCar(long id) {
        Car car = this.byId.remove(id);

        if(car == null){
            throw new UnsupportedOperationException();
        }

        this.cars.remove(car);
        this.byLapTime.remove(car);
        this.byHorsePower.remove(car);
        this.byLapsCount.remove(car);

        return car;
    }

    @Override
    public int size() {
        return this.cars.size();
    }

    @Override
    public Collection<Car> getCarsByBestLapTime() {

        if (this.byLapTime.isEmpty()){
            return new HashSet<>();
        } else {
            return this.byLapTime;
        }

    }

    @Override
    public Collection<Car> getCarsByHorsePower() {

        if (this.byHorsePower.isEmpty()){
            return new HashSet<>();
        } else {
            return this.byHorsePower;
        }
    }

    @Override
    public Collection<Car> getCarsByLapsCount() {

        if (this.byLapsCount.isEmpty()){
            return new HashSet<>();
        } else {
            return this.byLapsCount;
        }
    }

    @Override
    public Collection<Car> getCarsJoinedAfter(Date date) {

        return this.cars.stream()
                .filter(c -> c.getJoinedOn().after(date))
                .sorted(Comparator.comparing(Car::getJoinedOn))
                .collect(Collectors.toList());

    }

    @Override
    public Collection<Car> getCarsJoinedBefore(Date date) {
        return this.cars.stream()
                .filter(c -> c.getJoinedOn().before(date))
                .sorted(Comparator.comparing(Car::getJoinedOn))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Car> getAllOrdered() {

        if (this.cars.isEmpty()){
            return new HashSet<>();
        } else {
            return this.cars;
        }
    }
}
