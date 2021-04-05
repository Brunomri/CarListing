package com.bruno.carlisting.services;

import com.bruno.carlisting.domain.Car;
import com.bruno.carlisting.domain.User;
import com.bruno.carlisting.exceptions.ObjectNotFoundException;
import com.bruno.carlisting.repositories.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    
    private final CarRepository carRepository;
    private final UserService userService;

    public CarServiceImpl(CarRepository carRepository, UserService userService) {
        this.carRepository = carRepository;
        this.userService = userService;
    }

    @Override
    public Page<Car> getAllCars(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        return carRepository.findAll(pageRequest);
    }

    @Override
    public Car getCarById(Long carId) {
        Optional<Car> car = carRepository.findById(carId);
        return car.orElseThrow(() -> new ObjectNotFoundException("Car not found! Id: " + carId));
    }

    @Override
    public Page<Car> getCarsByMake(String searchMake, int page, int size) {

        Pageable pageRequest = PageRequest.of(page, size);
        return carRepository.searchCarsByMake(searchMake, pageRequest);
    }

    @Override
    public Page<Car> getCarsByUserId(Long userId, int page, int size) {

        Pageable pageRequest = PageRequest.of(page, size);
        return carRepository.findByUser_UserId(userId, pageRequest);
    }

    @Override
    public Car createCar(Car newCar, Long userId) {
        User user = userService.getUserById(userId);
        newCar.setUser(user);
        return carRepository.save(newCar);
    }

    @Override
    public Car updateCar(Car updatedCar, Long carId) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        optionalCar.orElseThrow(() -> new ObjectNotFoundException("Car not found! Id: " + carId));
        Car currentCar = optionalCar.get();

        currentCar.setMake(updatedCar.getMake());
        currentCar.setModel(updatedCar.getModel());
        currentCar.setYear(updatedCar.getYear());
        currentCar.setTrim(updatedCar.getTrim());
        currentCar.setColor(updatedCar.getColor());
        currentCar.setTransmission(updatedCar.getTransmission());
        currentCar.setFuel(updatedCar.getFuel());
        currentCar.setUser(userService.getUserByCarId(carId));

        return carRepository.save(currentCar);
    }

    @Override
    public void deleteCar(Long carId) {
        carRepository.deleteById(carId);
    }
}
