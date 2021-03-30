package com.bruno.carlisting.bootstrap;

import com.bruno.carlisting.domain.Car;
import com.bruno.carlisting.repositories.CarRepository;
import com.bruno.carlisting.services.CarService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class Bootstrap implements CommandLineRunner {

    private final CarRepository carRepository;
    private final CarService carService;

    public Bootstrap(CarRepository carRepository, CarService carService) {
        this.carRepository = carRepository;
        this.carService = carService;
    }

    @Override
    public void run(String... args) throws Exception {
        test();
    }

    private void test() {
        Pageable pageRequest = PageRequest.of(0, 1);
        Page<Car> carPages = carRepository.searchCarsByMake("Honda", pageRequest);
        System.out.println(carPages.getContent().toString());
        Car car = carPages.getContent().get(0);
        System.out.println(car.getMake());

        carPages = carService.getCarsByMake("Honda", 0, 2);
        car = carPages.getContent().get(0);
        System.out.println(car.getMake());

        carPages = carService.getAllCars(0, 2);
        car = carPages.getContent().get(0);
        System.out.println(car.getMake());
        car = carPages.getContent().get(1);
        System.out.println(car.getMake());
    }

}
