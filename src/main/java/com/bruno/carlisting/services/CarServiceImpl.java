package com.bruno.carlisting.services;

import com.bruno.carlisting.domain.Car;
import com.bruno.carlisting.repositories.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {
    
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
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
}
