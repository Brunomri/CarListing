package com.bruno.carlisting.repositories;

import com.bruno.carlisting.domain.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {


}
