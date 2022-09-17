package com.aerotravel.flightticketbooking.repository;

import com.aerotravel.flightticketbooking.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    List<Aircraft> findByModel(String model);

    List<Aircraft> findByManufacturer(String manufacturer);
}
