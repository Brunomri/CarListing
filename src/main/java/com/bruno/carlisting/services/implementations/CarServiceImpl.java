package com.bruno.carlisting.services.implementations;

import com.bruno.carlisting.domain.Car;
import com.bruno.carlisting.exceptions.ObjectNotFoundException;
import com.bruno.carlisting.exceptions.entityRelationshipIntegrityException;
import com.bruno.carlisting.repositories.CarRepository;
import com.bruno.carlisting.services.interfaces.CarService;
import com.bruno.carlisting.services.interfaces.PagingService;
import com.bruno.carlisting.services.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CarServiceImpl implements CarService {

    public static final String CAR_ID_NOT_FOUND = "Car ID %s not found";
    public static final String PAGE_HAS_NO_CARS = "Page %s has no cars";
    public static final String CARS_OF_MAKE_NOT_FOUND = "No cars of make %s were found on page %s";
    public static final String CARS_NOT_FOUND_FOR_USER_ID = "Cars not found for user ID = %s on page %s";
    public static final String CAR_ALREADY_EXISTS = "This car already exists:" +
            " Make = %s -" +
            " Model = %s -" +
            " Year = %s -" +
            " Trim = %s";

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

        var pageRequest = PageRequest.of(page, size);
        var carsPage = carRepository.findAll(pageRequest);

        log.debug("method = getAllCars, page number = {}, page size = {}, carsPage = {}",
                carsPage.getPageable().getPageNumber(), carsPage.getPageable().getPageSize(), carsPage.getContent());

        pagingService.validatePage(carsPage, String.format(PAGE_HAS_NO_CARS, page));

        return carsPage;
    }

    @Override
    public Car getCarById(Long carId) {

        var car = carRepository.findById(carId);

        log.debug("method = getCarById, car = {}", car);

        return car.orElseThrow(() -> new ObjectNotFoundException(String.format(CAR_ID_NOT_FOUND, carId)));
    }

    @Override
    public Page<Car> getCarsByMake(String searchMake, int page, int size) {

        var pageRequest = PageRequest.of(page, size);
        var carsPage = carRepository.findByMake(searchMake, pageRequest);

        log.debug("method = getCarsByMake, make = {}, page number = {}, page size = {}, carsPage = {}",
                searchMake, carsPage.getPageable().getPageNumber(),
                carsPage.getPageable().getPageSize(), carsPage.getContent());

        pagingService.validatePage(carsPage, String.format(CARS_OF_MAKE_NOT_FOUND, searchMake, page));

        return carsPage;
    }

    @Override
    public Page<Car> getCarsByUserId(Long userId, int page, int size) {

        var pageRequest = PageRequest.of(page, size);
        var carsPage = carRepository.findByUser_UserId(userId, pageRequest);

        log.debug("method = getCarsByUserId, userId = {}, page number = {}, page size = {}, carsPage = {}",
                userId, carsPage.getPageable().getPageNumber(),
                carsPage.getPageable().getPageSize(), carsPage.getContent());

        pagingService.validatePage(carsPage, String.format(CARS_NOT_FOUND_FOR_USER_ID, userId, page));

        return carsPage;
    }

    @Override
    public Car createCar(Car newCar, Long userId) {

        var user = userService.getUserById(userId);
        newCar.setUser(user);
        try {

            log.debug("method = createCar, newCar = {}, userId = {}", newCar, userId);

            return carRepository.save(newCar);
        } catch (DataIntegrityViolationException e) {

            log.warn("Data integrity violation exception occurred:", e);

            throw new entityRelationshipIntegrityException(String.format(
                    CAR_ALREADY_EXISTS, newCar.getMake(), newCar.getModel(), newCar.getYear(), newCar.getTrim()));
        }
    }

    @Override
    public Car updateCar(Car updatedCar, Long userId, Long carId) {

        var optionalCar = carRepository.findById(carId);
        var currentCar = optionalCar.orElseThrow(() -> new ObjectNotFoundException(
                String.format(CAR_ID_NOT_FOUND, carId)));

        log.debug("method = updateCar, currentCar = {}", currentCar);

        currentCar.setMake(updatedCar.getMake());
        currentCar.setModel(updatedCar.getModel());
        currentCar.setYear(updatedCar.getYear());
        currentCar.setTrim(updatedCar.getTrim());
        currentCar.setColor(updatedCar.getColor());
        currentCar.setTransmission(updatedCar.getTransmission());
        currentCar.setFuel(updatedCar.getFuel());
        currentCar.setUser(userService.getUserById(userId));

        log.debug("method = updateCar, updatedCar = {}", currentCar);

        try {
            return carRepository.save(currentCar);
        } catch (DataIntegrityViolationException e) {

            log.warn("Entity relationship integrity exception occurred:", e);

            throw new entityRelationshipIntegrityException(String.format(
                    CAR_ALREADY_EXISTS, currentCar.getMake(), currentCar.getModel(),
                    currentCar.getYear(), currentCar.getTrim()));
        }
    }

    @Override
    public Car updateCarMake(String make, Long carId) {

        var currentCar = getCarById(carId);

        log.debug("method = updateCarMake, currentCar: {}", currentCar);

        currentCar.setMake(make);
        try {
            return carRepository.save(currentCar);
        } catch (DataIntegrityViolationException e) {
            throw new entityRelationshipIntegrityException(String.format(CAR_ALREADY_EXISTS, currentCar.getMake(),
                    currentCar.getModel(), currentCar.getYear(), currentCar.getTrim()));
        }
    }

    @Override
    public Car updateCarModel(String model, Long carId) {

        var currentCar = getCarById(carId);

        currentCar.setModel(model);
        try {
            return carRepository.save(currentCar);
        } catch (DataIntegrityViolationException e) {
            throw new entityRelationshipIntegrityException(String.format(CAR_ALREADY_EXISTS, currentCar.getMake(),
                    currentCar.getModel(), currentCar.getYear(), currentCar.getTrim()));
        }
    }

    @Override
    public Car updateCarYear(Integer year, Long carId) {

        var currentCar = getCarById(carId);

        currentCar.setYear(year);
        try {
            return carRepository.save(currentCar);
        } catch (DataIntegrityViolationException e) {
            throw new entityRelationshipIntegrityException(String.format(CAR_ALREADY_EXISTS, currentCar.getMake(),
                    currentCar.getModel(), currentCar.getYear(), currentCar.getTrim()));
        }
    }

    @Override
    public Car updateCarTrim(String trim, Long carId) {

        var currentCar = getCarById(carId);

        currentCar.setTrim(trim);
        try {
            return carRepository.save(currentCar);
        } catch (DataIntegrityViolationException e) {
            throw new entityRelationshipIntegrityException(String.format(CAR_ALREADY_EXISTS, currentCar.getMake(),
                    currentCar.getModel(), currentCar.getYear(), currentCar.getTrim()));
        }
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

        var carToDelete = carRepository.findById(carId);
        carRepository.delete(carToDelete.orElseThrow(() -> new ObjectNotFoundException(
                String.format(CAR_ID_NOT_FOUND, carId))));
    }
}
