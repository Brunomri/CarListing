package com.bruno.carlisting.controller;

import com.bruno.carlisting.domain.Car;
import com.bruno.carlisting.services.CarService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.net.URI;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/cars")
@Validated
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
    public ResponseEntity<Page<Car>> findAllCars(@RequestParam(value = "page", required = false, defaultValue = "0")
                                 @Min(value = 0, message = "Page number must be greater than or equal to 0") int page,
                                 @RequestParam(value = "size", required = false, defaultValue = "1")
                                 @Min(value = 1, message = "Page size must be greater than or equal to 1")
                                 @Max(value = 10, message = "Page size must be less than or equal to 10") int size) {

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
    public ResponseEntity<Car> findCarById(@PathVariable @NotNull(message = "Car ID is mandatory") Long carId) {

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
    public ResponseEntity<Page<Car>> findCarsByMake(@PathVariable @NotBlank(message = "Make is mandatory")
                                @Size(max = 30, message = "Make must have 30 characters or less") String make,
                                @RequestParam(value = "page", required = false, defaultValue = "0")
                                @Min(value = 0, message = "Page number must be greater than or equal to 0") int page,
                                @RequestParam(value = "size", required = false, defaultValue = "1")
                                @Min(value = 1, message = "Page size must be greater than or equal to 1")
                                @Max(value = 10, message = "Page size must be less than or equal to 10") int size) {

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
    public ResponseEntity<Page<Car>> findCarsByUserId(@PathVariable @NotNull(message = "User ID is mandatory") Long userId,
                                  @RequestParam(value = "page", required = false, defaultValue = "0")
                                  @Min(value = 0, message = "Page number must be greater than or equal to 0") int page,
                                  @RequestParam(value = "size", required = false, defaultValue = "1")
                                  @Min(value = 1, message = "Page size must be greater than or equal to 1")
                                  @Max(value = 10, message = "Page size must be less than or equal to 10") int size) {

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
    public ResponseEntity<Car> createCar(@PathVariable @NotNull(message = "User ID is mandatory") Long userId,
                                         @Valid @RequestBody Car car) {

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
    public ResponseEntity<Car> updateCar(@PathVariable @NotNull(message = "Car ID is mandatory") Long carId,
                                         @Valid @RequestBody Car car) {

        Car updatedCar = carService.updateCar(car, carId);
        return ResponseEntity.ok().body(updatedCar);
    }

    @ApiOperation(value = "Delete an existing car")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Car deleted"),
            @ApiResponse(code = 500, message = "Server exception"),
    })
    @DeleteMapping(value = "/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable @NotNull(message = "Car ID is mandatory") Long carId) {

        carService.deleteCar(carId);
        return ResponseEntity.noContent().build();
    }
}
