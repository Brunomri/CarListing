package com.bruno.carlisting.controller;

import com.bruno.carlisting.domain.Car;
import com.bruno.carlisting.services.CarService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<Page<Car>> findAllCars(@RequestParam(value = "page", required = false,
                                                 defaultValue = "0") int page,
                                                 @RequestParam(value = "size", required = false,
                                                 defaultValue = "10") int size) {

        Page<Car> carsPage = carService.getAllCars(page, size);
        if(carsPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok().body(carsPage);
        }

    }

    @GetMapping(value = "/{carId}")
    public ResponseEntity<Car> findCarById(@PathVariable Long carId) {
        Car car = carService.getCarById(carId);
        return ResponseEntity.ok().body(car);
    }

    @GetMapping(value = "/make/{make}")
    public ResponseEntity<Page<Car>> findCarsByMake(@PathVariable String make,
                                                    @RequestParam(value = "page", required = false,
                                                    defaultValue = "0") int page,
                                                    @RequestParam(value = "size", required = false,
                                                    defaultValue = "10") int size) {

        Page<Car> carsPage = carService.getCarsByMake(make, page, size);
        if(carsPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok().body(carsPage);
        }

    }

    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<Page<Car>> findCarsByUserId(@PathVariable Long userId,
                                        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                        @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        Page<Car> carsPage = carService.getCarsByUserId(userId, page, size);
        if(carsPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok().body(carsPage);
        }

    }

    @PostMapping(value = "/{userId}")
    public ResponseEntity<Car> createCar(@PathVariable Long userId, @Valid @RequestBody Car car) {

        Car newCar = carService.createCar(car, userId);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/cars/{carId}").
                buildAndExpand(newCar.getCarId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{carId}")
    public ResponseEntity<Car> updateCar(@PathVariable Long carId, @Valid @RequestBody Car car) {

        Car updatedCar = carService.updateCar(car, carId);
        return ResponseEntity.ok().body(updatedCar);
    }

    @DeleteMapping(value = "/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long carId) {

        carService.deleteCar(carId);
        return ResponseEntity.noContent().build();
    }
}
