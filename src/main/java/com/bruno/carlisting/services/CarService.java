package com.bruno.carlisting.services;

import com.bruno.carlisting.domain.Car;
import org.springframework.data.domain.Page;

public interface CarService {

    Page<Car> getAllCars(int page, int size);

    Car getCarById(Long carId);

    Page<Car> getCarsByMake(String searchMake, int page, int size);

    Page<Car> getCarsByUserId(Long userId, int page, int size);

    Car createCar(Car newCar, Long userId);

    Car updateCar(Car car, Long carId);

    void deleteCar(Long carId);

}
