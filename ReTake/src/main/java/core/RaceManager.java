package core;

import models.Car;

import java.util.Collection;
import java.util.Date;

public class RaceManager implements Race {

    @Override
    public void add(Car car) {

    }

    @Override
    public boolean contains(Car car) {
        return false;
    }

    @Override
    public Car getCar(long id) {
        return null;
    }

    @Override
    public Car removeCar(long id) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Collection<Car> getCarsByBestLapTime() {
        return null;
    }

    @Override
    public Collection<Car> getCarsByHorsePower() {
        return null;
    }

    @Override
    public Collection<Car> getCarsByLapsCount() {
        return null;
    }

    @Override
    public Collection<Car> getCarsJoinedAfter(Date date) {
        return null;
    }

    @Override
    public Collection<Car> getCarsJoinedBefore(Date date) {
        return null;
    }

    @Override
    public Collection<Car> getAllOrdered() {
        return null;
    }
}
