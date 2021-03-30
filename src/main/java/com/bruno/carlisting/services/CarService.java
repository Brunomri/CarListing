package com.bruno.carlisting.services;

import com.bruno.carlisting.domain.Car;
import org.springframework.data.domain.Page;

public interface CarService {

    Page<Car> getAllCars(int page, int size);

    Page<Car> getCarsByMake(String searchMake, int page, int size);

}
