package com.bruno.carlisting.controller;

import com.bruno.carlisting.domain.Car;
import com.bruno.carlisting.services.CarService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Return all cars grouped in pages")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a page of cars"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Server exception"),
    })
    @GetMapping(produces = "application/json")
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

    @ApiOperation(value = "Find a car by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return the car with corresponding ID"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Server exception"),
    })
    @GetMapping(value = "/{carId}", produces = "application/json")
    public ResponseEntity<Car> findCarById(@PathVariable Long carId) {
        Car car = carService.getCarById(carId);
        return ResponseEntity.ok().body(car);
    }

    @ApiOperation(value = "Find all cars by make")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a page of cars from the corresponding make"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Server exception"),
    })
    @GetMapping(value = "/make/{make}", produces = "application/json")
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

    @ApiOperation(value = "Find all cars by user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a page of cars added by a certain user"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Server exception"),
    })
    @GetMapping(value = "/users/{userId}", produces = "application/json")
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

    @ApiOperation(value = "Add a new car")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New car created"),
            @ApiResponse(code = 500, message = "Server exception"),
    })
    @PostMapping(value = "/{userId}", consumes = "application/json")
    public ResponseEntity<Car> createCar(@PathVariable Long userId, @Valid @RequestBody Car car) {

        Car newCar = carService.createCar(car, userId);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/cars/{carId}").
                buildAndExpand(newCar.getCarId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "Update an existing car")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Car updated"),
            @ApiResponse(code = 500, message = "Server exception"),
    })
    @PutMapping(value = "/{carId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Car> updateCar(@PathVariable Long carId, @Valid @RequestBody Car car) {

        Car updatedCar = carService.updateCar(car, carId);
        return ResponseEntity.ok().body(updatedCar);
    }

    @ApiOperation(value = "Delete an existing car")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Car deleted"),
            @ApiResponse(code = 500, message = "Server exception"),
    })
    @DeleteMapping(value = "/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long carId) {

        carService.deleteCar(carId);
        return ResponseEntity.noContent().build();
    }
}
