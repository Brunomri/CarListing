package com.bruno.carlisting.dtos.response.car;

import com.bruno.carlisting.domain.Car;
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

    private Long carId;
    private String make;
    private String model;
    private Integer year;
    private String trim;
    private String color;
    private String transmission;
    private String fuel;

    private List<Long> carListingsIds;

    public static CarPublicResponseDTO toCarPublicDTO(Car car) {
        List<Long> carListingsIds = new ArrayList<>();
        car.getCarListings().forEach(listing -> carListingsIds.add(listing.getListingId()));
        return new CarPublicResponseDTO(car.getCarId(), car.getMake(), car.getModel(), car.getYear(), car.getTrim(), car.getColor(),
                car.getTransmission(), car.getFuel(), carListingsIds);
    }

    public static Page<CarPublicResponseDTO> toCarPublicDTO(Page<Car> carsPage) {
        List<CarPublicResponseDTO> carsListDTO = new ArrayList<>();
        carsPage.forEach(car -> {
            List<Long> carListingsIds = new ArrayList<>();
            car.getCarListings().forEach(listing -> carListingsIds.add(listing.getListingId()));
            var carDTO = new CarPublicResponseDTO(car.getCarId(), car.getMake(), car.getModel(), car.getYear(),
                    car.getTrim(), car.getColor(), car.getTransmission(), car.getFuel(), carListingsIds);
            carsListDTO.add(carDTO);
        });
        return new PageImpl<>(carsListDTO, carsPage.getPageable(), carsPage.getTotalElements());
    }
}
