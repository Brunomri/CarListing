package com.bruno.carlisting.controller;

import com.bruno.carlisting.domain.Car;
import com.bruno.carlisting.services.CarService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/cars")
public class CarController {

    private CarService carService;

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

    @GetMapping(value = "/{make}")
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
}
