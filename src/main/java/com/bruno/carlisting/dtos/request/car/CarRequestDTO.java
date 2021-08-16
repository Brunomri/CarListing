package com.bruno.carlisting.dtos.request.car;

import com.bruno.carlisting.domain.Car;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
public class CarRequestDTO {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Make is mandatory")
    @Size(max = 30, message = "Make must have 30 characters or less")
    private String make;

    @NotBlank(message = "Model is mandatory")
    @Size(max = 30, message = "Model must have 30 characters or less")
    private String model;

    @NotNull(message = "Year is mandatory")
    @Min(value = 1900, message = "Year must be greater than or equal to 1900")
    @Max(value = 9999, message = "Year must have 4 digits")
    private Integer year;

    @NotBlank(message = "Trim is mandatory")
    @Size(max = 30, message = "Trim must have 30 characters or less")
    private String trim;

    @NotBlank(message = "Color is mandatory")
    @Size(max = 30, message = "Color must have 30 characters or less")
    private String color;

    @Pattern(regexp = "^(AT|MT)$",
            message = "Transmission must be either AT for automatic transmission or MT for manual transmission")
    private String transmission;

    @Pattern(regexp = "^(Gasoline|Ethanol|Flex-Fuel|Electricity|Hybrid)$",
            message = "Fuel must be one of the following: Gasoline, Ethanol, Flex-Fuel, Electricity or Hybrid")
    private String fuel;

    @Positive(message = "Responsible User ID must be a positive integer")
    private Long userId;

    public Car toCar() {
        return new Car(make, model, year, trim, color, transmission, fuel);
    }

    @Override
    public String toString() {
        return String.format(
                "\nmake = %s\n" +
                "model = %s\n" +
                "year = %s\n" +
                "trim = %s\n" +
                "color = %s\n" +
                "transmission = %s\n" +
                "fuel = %s\n",
                this.getMake(), this.getModel(), this.getYear(), this.getTrim(),
                this.getColor(), this.getTransmission(), this.getFuel());
    }
}
