package com.bruno.carlisting.dtos.response.car;

import com.bruno.carlisting.domain.Car;
import com.bruno.carlisting.domain.Listing;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CarPublicResponseDTO {

    private static final long serialVersionUID = 1L;

    private String make;
    private String model;
    private Integer year;
    private String trim;
    private String color;
    private String transmission;
    private String fuel;

    private List<Listing> carListings;

    public CarPublicResponseDTO toCarPublicDTO(Car car) {
        return new CarPublicResponseDTO(car.getMake(), car.getModel(), car.getYear(), car.getTrim(), car.getColor(),
                car.getTransmission(), car.getFuel(), car.getCarListings());
    }

    public static Page<CarPublicResponseDTO> toCarsPagePublicDTO(Page<Car> carsPage) {
        List<CarPublicResponseDTO> carsListDTO = new ArrayList<>();
        carsPage.forEach(car -> {
            var carDTO = new CarPublicResponseDTO(car.getMake(), car.getModel(), car.getYear(),
                    car.getTrim(), car.getColor(), car.getTransmission(), car.getFuel(), car.getCarListings());
            carsListDTO.add(carDTO);
        });
        return new PageImpl<>(carsListDTO, carsPage.getPageable(), carsPage.getTotalElements());
    }
}
