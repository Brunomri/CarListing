package com.bruno.carlisting.services;

import com.bruno.carlisting.domain.Car;
import com.bruno.carlisting.domain.User;
import com.bruno.carlisting.repositories.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Page<Car> getCarsByMake(String searchMake, int page, int size) {

        Pageable pageRequest = PageRequest.of(page, size);
        return carRepository.searchCarsByMake(searchMake, pageRequest);
    }

    @Override
    public Car createCar(Car newCar, Long userId) {
        User user = userService.getUserById(userId);
        newCar.setUser(user);
        return carRepository.save(newCar);
    }
}
