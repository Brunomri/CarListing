package com.bruno.carlisting.repositories;

import com.bruno.carlisting.domain.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

//    @Query(value = "SELECT * FROM car WHERE make = :searchMake", nativeQuery = true)
//    Page<Car> searchCarsByMake(@Param("searchMake") String searchMake, Pageable pageable);
    Page<Car> findByMake(String make, Pageable pageRequest);

    Page<Car> findByUser_UserId(Long userId, Pageable pageRequest);
}
