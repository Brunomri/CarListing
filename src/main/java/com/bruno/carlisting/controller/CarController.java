package com.bruno.carlisting.controller;

import com.bruno.carlisting.domain.Car;
import com.bruno.carlisting.dtos.request.car.CarColorRequestDTO;
import com.bruno.carlisting.dtos.request.car.CarFuelRequestDTO;
import com.bruno.carlisting.dtos.request.car.CarMakeRequestDTO;
import com.bruno.carlisting.dtos.request.car.CarModelRequestDTO;
import com.bruno.carlisting.dtos.request.car.CarRequestDTO;
import com.bruno.carlisting.dtos.request.car.CarTransmissionRequestDTO;
import com.bruno.carlisting.dtos.request.car.CarTrimRequestDTO;
import com.bruno.carlisting.dtos.request.car.CarUserRequestDTO;
import com.bruno.carlisting.dtos.request.car.CarYearRequestDTO;
import com.bruno.carlisting.dtos.response.car.CarPrivateResponseDTO;
import com.bruno.carlisting.dtos.response.car.CarPublicResponseDTO;
import com.bruno.carlisting.services.interfaces.CarService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/cars")
@Validated
public class CarController {

    private static final String CAR_PAGE_DEFAULT_NUMBER = "0";
    private static final String CAR_PAGE_DEFAULT_SIZE = "1";
    private static final int CAR_PAGE_MIN_NUMBER = 0;
    private static final int CAR_PAGE_MIN_SIZE = 1;
    private static final int CAR_PAGE_MAX_SIZE = 10;

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @ApiOperation(value = "Return all cars grouped in pages")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Return a page of cars"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Page content not found"),
        @ApiResponse(code = 500, message = "Server exception"),
    })
    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<Page<CarPublicResponseDTO>> findAllCars(

        @RequestParam(value = "page", required = false, defaultValue = CAR_PAGE_DEFAULT_NUMBER)
        @Min(value = CAR_PAGE_MIN_NUMBER,
            message = "Page number must be greater than or equal to " + CAR_PAGE_MIN_NUMBER) int page,

        @RequestParam(value = "size", required = false, defaultValue = CAR_PAGE_DEFAULT_SIZE)
        @Min(value = CAR_PAGE_MIN_SIZE,
            message = "Page size must be greater than or equal to " + CAR_PAGE_MIN_SIZE)
        @Max(value = CAR_PAGE_MAX_SIZE,
            message = "Page size must be less than or equal to " + CAR_PAGE_MAX_SIZE) int size) {

        var carsPageDTO = CarPublicResponseDTO.toCarsPagePublicDTO(
                carService.getAllCars(page, size));
        return ResponseEntity.ok().body(carsPageDTO);
    }

    @ApiOperation(value = "Find a car by ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Return the car with corresponding ID"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Car not found"),
        @ApiResponse(code = 500, message = "Server exception"),
    })
    @GetMapping(value = "/{carId}", produces = "application/json")
    public ResponseEntity<Car> findCarById(

        @PathVariable @Positive(message = "Car ID must be a positive integer") Long carId) {

        var car = carService.getCarById(carId);
        return ResponseEntity.ok().body(car);
    }

    @ApiOperation(value = "Find all cars by make")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Return a page of cars from the corresponding make"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Page content not found"),
        @ApiResponse(code = 500, message = "Server exception"),
    })
    @GetMapping(value = "/make/{make}", produces = "application/json")
    public ResponseEntity<Page<CarPublicResponseDTO>> findCarsByMake(

        @PathVariable @NotBlank(message = "Make is mandatory")
        @Size(max = 30, message = "Make must have 30 characters or less") String make,

        @RequestParam(value = "page", required = false, defaultValue = CAR_PAGE_DEFAULT_NUMBER)
        @Min(value = CAR_PAGE_MIN_NUMBER,
                message = "Page number must be greater than or equal to " + CAR_PAGE_MIN_NUMBER) int page,

        @RequestParam(value = "size", required = false, defaultValue = CAR_PAGE_DEFAULT_SIZE)
        @Min(value = CAR_PAGE_MIN_SIZE,
                message = "Page size must be greater than or equal to " + CAR_PAGE_MIN_SIZE)
        @Max(value = CAR_PAGE_MAX_SIZE,
                message = "Page size must be less than or equal to " + CAR_PAGE_MAX_SIZE) int size) {

        var carsPageDTO = CarPublicResponseDTO.toCarsPagePublicDTO(
                carService.getCarsByMake(make, page, size));
        return ResponseEntity.ok().body(carsPageDTO);
    }

    @ApiOperation(value = "Find all cars by user")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Return a page of cars added by a certain user"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Page content not found"),
        @ApiResponse(code = 500, message = "Server exception"),
    })
    @GetMapping(value = "/users/{userId}", produces = "application/json")
    public ResponseEntity<Page<CarPublicResponseDTO>> findCarsByUserId(

            @PathVariable @Positive(message = "User ID must be a positive integer") Long userId,

            @RequestParam(value = "page", required = false, defaultValue = CAR_PAGE_DEFAULT_NUMBER)
            @Min(value = CAR_PAGE_MIN_NUMBER,
                    message = "Page number must be greater than or equal to " + CAR_PAGE_MIN_NUMBER) int page,

            @RequestParam(value = "size", required = false, defaultValue = CAR_PAGE_DEFAULT_SIZE)
            @Min(value = CAR_PAGE_MIN_SIZE,
                    message = "Page size must be greater than or equal to " + CAR_PAGE_MIN_SIZE)
            @Max(value = CAR_PAGE_MAX_SIZE,
                    message = "Page size must be less than or equal to " + CAR_PAGE_MAX_SIZE) int size) {

        var carsPageDTO = CarPublicResponseDTO.toCarsPagePublicDTO(
                carService.getCarsByUserId(userId, page, size));
        return ResponseEntity.ok().body(carsPageDTO);
    }

    @ApiOperation(value = "Add a new car")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "New car created"),
        @ApiResponse(code = 400, message = "Invalid Car data provided"),
        @ApiResponse(code = 500, message = "Server exception"),
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<CarPrivateResponseDTO> createCar(

        @Valid @RequestBody CarRequestDTO carRequestDTO) {

        var newCar = carService.createCar(carRequestDTO.toCar(), carRequestDTO.getUserId());
        var uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/cars/{carId}").
                buildAndExpand(newCar.getCarId()).toUri();
        return ResponseEntity.created(uri).body(CarPrivateResponseDTO.toCarPrivateDTO(newCar));
    }

    @ApiOperation(value = "Update an existing car")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Car updated"),
        @ApiResponse(code = 400, message = "Invalid Car data provided"),
        @ApiResponse(code = 404, message = "Car not found"),
        @ApiResponse(code = 500, message = "Server exception"),
    })
    @PutMapping(value = "/{carId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CarPrivateResponseDTO> updateCar(

        @PathVariable @Positive(message = "Car ID must be a positive integer") Long carId,

        @Valid @RequestBody CarRequestDTO carRequestDTO) {

        var updatedCar = carService.updateCar(carRequestDTO.toCar(), carRequestDTO.getUserId(), carId);
        return ResponseEntity.ok().body(CarPrivateResponseDTO.toCarPrivateDTO(updatedCar));
    }

    @ApiOperation(value = "Update a car's make")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Car's make updated"),
            @ApiResponse(code = 404, message = "Car not found"),
            @ApiResponse(code = 500, message = "Server exception"),
    })
    @PatchMapping(value = "/{carId}/make", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CarPrivateResponseDTO> updateCarMake(
            @PathVariable @Positive(message = "Car ID must be a positive integer") Long carId,

            @Valid @RequestBody CarMakeRequestDTO carMakeRequestDTO) {

        var updatedCar = carService.updateCarMake(carMakeRequestDTO.getMake(), carId);
        return ResponseEntity.ok().body(CarPrivateResponseDTO.toCarPrivateDTO(updatedCar));
    }

    @ApiOperation(value = "Update a car's model")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Car's model updated"),
            @ApiResponse(code = 404, message = "Car not found"),
            @ApiResponse(code = 500, message = "Server exception")
    })
    @PatchMapping(value = "/{carId}/model", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CarPrivateResponseDTO> updateCarModel(
            @PathVariable @Positive(message = "Car ID must be a positive integer") Long carId,

            @Valid @RequestBody CarModelRequestDTO carModelRequestDTO) {

        var updatedCar = carService.updateCarModel(carModelRequestDTO.getModel(), carId);
        return ResponseEntity.ok().body(CarPrivateResponseDTO.toCarPrivateDTO(updatedCar));
    }

    @ApiOperation(value = "Update a car's year")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Car's year updated"),
            @ApiResponse(code = 404, message = "Car not found"),
            @ApiResponse(code = 500, message = "Server exception")
    })
    @PatchMapping(value = "/{carId}/year", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CarPrivateResponseDTO> updateCarYear(
            @PathVariable @Positive(message = "Car ID must be a positive integer") Long carId,

            @Valid @RequestBody CarYearRequestDTO carYearRequestDTO) {

        var updatedCar = carService.updateCarYear(carYearRequestDTO.getYear(), carId);
        return ResponseEntity.ok().body(CarPrivateResponseDTO.toCarPrivateDTO(updatedCar));
    }

    @ApiOperation(value = "Update a car's trim")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Car's trim updated"),
            @ApiResponse(code = 404, message = "Car not found"),
            @ApiResponse(code = 500, message = "Server exception")
    })
    @PatchMapping(value = "/{carId}/trim", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CarPrivateResponseDTO> updateCarTrim(
            @PathVariable @Positive(message = "Car ID must be a positive integer") Long carId,

            @Valid @RequestBody CarTrimRequestDTO carTrimRequestDTO) {

        var updatedCar = carService.updateCarTrim(carTrimRequestDTO.getTrim(), carId);
        return ResponseEntity.ok().body(CarPrivateResponseDTO.toCarPrivateDTO(updatedCar));
    }

    @ApiOperation(value = "Update a car's color")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Car's color updated"),
            @ApiResponse(code = 404, message = "Car not found"),
            @ApiResponse(code = 500, message = "Server exception")
    })
    @PatchMapping(value = "/{carId}/color", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CarPrivateResponseDTO> updateCarColor(
            @PathVariable @Positive(message = "Car ID must be a positive integer") Long carId,

            @Valid @RequestBody CarColorRequestDTO carColorRequestDTO) {

        var updatedCar = carService.updateCarColor(carColorRequestDTO.getColor(), carId);
        return ResponseEntity.ok().body(CarPrivateResponseDTO.toCarPrivateDTO(updatedCar));
    }

    @ApiOperation(value = "Update a car's transmission")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Car's transmission updated"),
            @ApiResponse(code = 404, message = "Car not found"),
            @ApiResponse(code = 500, message = "Server exception")
    })
    @PatchMapping(value = "/{carId}/transmission", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CarPrivateResponseDTO> updateCarTransmission(
            @PathVariable @Positive(message = "Car ID must be a positive integer") Long carId,

            @Valid @RequestBody CarTransmissionRequestDTO carTransmissionRequestDTO) {

        var updatedCar = carService.updateCarTransmission(carTransmissionRequestDTO.getTransmission(), carId);
        return ResponseEntity.ok().body(CarPrivateResponseDTO.toCarPrivateDTO(updatedCar));
    }

    @ApiOperation(value = "Update a car's fuel")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Car's fuel updated"),
            @ApiResponse(code = 404, message = "Car not found"),
            @ApiResponse(code = 500, message = "Server exception")
    })
    @PatchMapping(value = "/{carId}/fuel", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CarPrivateResponseDTO> updateCarFuel(
            @PathVariable @Positive(message = "Car ID must be a positive integer") Long carId,

            @Valid @RequestBody CarFuelRequestDTO carFuelRequestDTO) {

        var updatedCar = carService.updateCarFuel(carFuelRequestDTO.getFuel(), carId);
        return ResponseEntity.ok().body(CarPrivateResponseDTO.toCarPrivateDTO(updatedCar));
    }

    @ApiOperation(value = "Update a car's responsible User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Car's responsible User updated"),
            @ApiResponse(code = 404, message = "Car not found"),
            @ApiResponse(code = 500, message = "Server exception")
    })
    @PatchMapping(value = "/{carId}/user", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CarPrivateResponseDTO> updateCarUser(
            @PathVariable @Positive(message = "Car ID must be a positive integer") Long carId,

            @Valid @RequestBody CarUserRequestDTO carUserRequestDTO) {

        var updatedCar = carService.updateCarUser(carUserRequestDTO.getUserId(), carId);
        return ResponseEntity.ok().body(CarPrivateResponseDTO.toCarPrivateDTO(updatedCar));
    }

    @ApiOperation(value = "Delete an existing car")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Car deleted"),
        @ApiResponse(code = 500, message = "Server exception"),
    })
    @DeleteMapping(value = "/{carId}")
    public ResponseEntity<Void> deleteCar(

        @PathVariable @Positive(message = "Car ID must be a positive integer") Long carId) {

        carService.deleteCar(carId);
        return ResponseEntity.noContent().build();
    }
}
