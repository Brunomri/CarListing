package com.bruno.carlisting.repositories;

import com.bruno.carlisting.domain.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(value = "SELECT car_id, model, year, trim FROM car WHERE make = :searchMake", nativeQuery = true)
    Page<Car> searchCarByMake(@Param("searchMake") String searchMake, Pageable pageable);

}
