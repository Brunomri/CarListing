package com.bruno.carlisting.repositories;

import com.bruno.carlisting.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT user_id FROM car WHERE car_id = :searchCarId", nativeQuery = true)
    Optional<Long> searchUserByCarId(@Param("searchCarId") Long searchCarId);
}
