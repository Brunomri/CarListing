package com.bruno.carlisting.dtos.response.car;

import com.bruno.carlisting.domain.Car;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CarPrivateResponseDTO {

    private static final long serialVersionUID = 1L;

    private Long carId;
    private String make;
    private String model;
    private Integer year;
    private String trim;
    private String color;
    private String transmission;
    private String fuel;

    private Long userId;
    private List<Long> carListingsIds;

    public static CarPrivateResponseDTO toCarPrivateDTO(Car car) {
        List<Long> carListingsIds = new ArrayList<>();
        car.getCarListings().forEach(listing -> carListingsIds.add(listing.getListingId()));
        return new CarPrivateResponseDTO(car.getCarId(), car.getMake(), car.getModel(), car.getYear(), car.getTrim(),
                car.getColor(), car.getTransmission(), car.getFuel(), car.getUser().getUserId(), carListingsIds);
    }
}
