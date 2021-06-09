package com.bruno.carlisting.services.implementations;

import com.bruno.carlisting.domain.Car;
import com.bruno.carlisting.exceptions.ObjectNotFoundException;
import com.bruno.carlisting.exceptions.entityRelationshipIntegrityException;
import com.bruno.carlisting.repositories.CarRepository;
import com.bruno.carlisting.services.interfaces.CarService;
import com.bruno.carlisting.services.interfaces.PagingService;
import com.bruno.carlisting.services.interfaces.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    public static final String CAR_ID_NOT_FOUND = "Car ID %s not found";

    private final CarRepository carRepository;
    private final UserService userService;
    private final PagingService pagingService;

    public CarServiceImpl(CarRepository carRepository, UserService userService, PagingService pagingService) {

        this.carRepository = carRepository;
        this.userService = userService;
        this.pagingService = pagingService;
    }

    @Override
    public Page<Car> getAllCars(int page, int size) {

        Pageable pageRequest = PageRequest.of(page, size);
        Page<Car> carsPage = carRepository.findAll(pageRequest);
        pagingService.validatePage(carsPage);
        return carsPage;
    }

    @Override
    public Car getCarById(Long carId) {

        Optional<Car> car = carRepository.findById(carId);
        return car.orElseThrow(() -> new ObjectNotFoundException(String.format(CAR_ID_NOT_FOUND, carId)));
    }

    @Override
    public Page<Car> getCarsByMake(String searchMake, int page, int size) {

        Pageable pageRequest = PageRequest.of(page, size);
        Page<Car> carsPage = carRepository.findByMake(searchMake, pageRequest);
        pagingService.validatePage(carsPage);
        return carsPage;
    }

    @Override
    public Page<Car> getCarsByUserId(Long userId, int page, int size) {

        Pageable pageRequest = PageRequest.of(page, size);
        Page<Car> carsPage = carRepository.findByUser_UserId(userId, pageRequest);
        pagingService.validatePage(carsPage);
        return carsPage;
    }

    @Override
    public Car createCar(Car newCar, Long userId) {

        var user = userService.getUserById(userId);
        newCar.setUser(user);
        try {
            return carRepository.save(newCar);
        } catch (DataIntegrityViolationException e) {
            throw new entityRelationshipIntegrityException(String.format(
                    "This car already exists:" +
                            " Make: %s -" +
                            " Model: %s -" +
                            " Year: %s -" +
                            " Trim: %s", newCar.getMake(), newCar.getModel(), newCar.getYear(), newCar.getTrim()));
        }
    }

    @Override
    public Car updateCar(Car updatedCar, Long userId, Long carId) {

        Optional<Car> optionalCar = carRepository.findById(carId);
        var currentCar = optionalCar.orElseThrow(() -> new ObjectNotFoundException(
                String.format(CAR_ID_NOT_FOUND, carId)));

        currentCar.setMake(updatedCar.getMake());
        currentCar.setModel(updatedCar.getModel());
        currentCar.setYear(updatedCar.getYear());
        currentCar.setTrim(updatedCar.getTrim());
        currentCar.setColor(updatedCar.getColor());
        currentCar.setTransmission(updatedCar.getTransmission());
        currentCar.setFuel(updatedCar.getFuel());
        currentCar.setUser(userService.getUserById(userId));

        try {
            return carRepository.save(currentCar);
        } catch (DataIntegrityViolationException e) {
            throw new entityRelationshipIntegrityException(String.format(
                    "This car already exists:" +
                            " Make: %s -" +
                            " Model: %s -" +
                            " Year: %s -" +
                            " Trim: %s", currentCar.getMake(), currentCar.getModel(),
                    currentCar.getYear(), currentCar.getTrim()));
        }
    }

    @Override
    public Car updateCarMake(String make, Long carId) {

        var currentCar = getCarById(carId);

        currentCar.setMake(make);
        return carRepository.save(currentCar);
    }

    @Override
    public Car updateCarModel(String model, Long carId) {

        var currentCar = getCarById(carId);

        currentCar.setModel(model);
        return carRepository.save(currentCar);
    }

    @Override
    public Car updateCarYear(Integer year, Long carId) {

        var currentCar = getCarById(carId);

        currentCar.setYear(year);
        return carRepository.save(currentCar);
    }

    @Override
    public Car updateCarTrim(String trim, Long carId) {

        var currentCar = getCarById(carId);

        currentCar.setTrim(trim);
        return carRepository.save(currentCar);
    }

    @Override
    public Car updateCarColor(String color, Long carId) {

        var currentCar = getCarById(carId);

        currentCar.setColor(color);
        return carRepository.save(currentCar);
    }

    @Override
    public Car updateCarTransmission(String transmission, Long carId) {

        var currentCar = getCarById(carId);

        currentCar.setTransmission(transmission);
        return carRepository.save(currentCar);
    }

    @Override
    public Car updateCarFuel(String fuel, Long carId) {

        var currentCar = getCarById(carId);

        currentCar.setFuel(fuel);
        return carRepository.save(currentCar);
    }

    @Override
    public Car updateCarUser(Long userId, Long carId) {

        var currentCar = getCarById(carId);

        currentCar.setUser(userService.getUserById(userId));
        return carRepository.save(currentCar);
    }

    @Override
    public void deleteCar(Long carId) {

        Optional<Car> carToDelete = carRepository.findById(carId);
        carRepository.delete(carToDelete.orElseThrow(() -> new ObjectNotFoundException(
                String.format(CAR_ID_NOT_FOUND, carId))));
    }
}
