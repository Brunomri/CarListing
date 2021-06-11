package com.bruno.carlisting.dtos.response.car;

import com.bruno.carlisting.domain.Car;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CarPrivateResponseDTO extends CarPublicResponseDTO {

    private Long userId;

    public CarPrivateResponseDTO(Long carId, String make, String model, Integer year, String trim, String color,
                                 String transmission, String fuel, List<Long> carListingsIds, Long userId) {
        super(carId, make, model, year, trim, color, transmission, fuel, carListingsIds);
        this.userId = userId;
    }

    public static CarPrivateResponseDTO toCarPrivateDTO(Car car) {
        List<Long> carListingsIds = new ArrayList<>();
        car.getCarListings().forEach(listing -> carListingsIds.add(listing.getListingId()));
        return new CarPrivateResponseDTO(car.getCarId(), car.getMake(), car.getModel(), car.getYear(), car.getTrim(),
                car.getColor(), car.getTransmission(), car.getFuel(), carListingsIds, car.getUser().getUserId());
    }
}
