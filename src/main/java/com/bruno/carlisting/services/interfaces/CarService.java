package com.bruno.carlisting.services.interfaces;

import com.bruno.carlisting.domain.Car;
import org.springframework.data.domain.Page;

public interface CarService {

    Page<Car> getAllCars(int page, int size);

    Car getCarById(Long carId);

    Page<Car> getCarsByMake(String searchMake, int page, int size);

    Page<Car> getCarsByUserId(Long userId, int page, int size);

    Car createCar(Car newCar, Long userId);

    Car updateCar(Car updatedCar, Long userId, Long carId);

    Car updateCarMake(String make, Long carId);

    Car updateCarModel(String model, Long carId);

    Car updateCarYear(Integer year, Long carId);

    Car updateCarTrim(String trim, Long carId);

    Car updateCarColor(String color, Long carId);

    Car updateCarTransmission(String transmission, Long carId);

    Car updateCarFuel(String fuel, Long carId);

    Car updateCarUser(Long userId, Long carId);

    void deleteCar(Long carId);
}
