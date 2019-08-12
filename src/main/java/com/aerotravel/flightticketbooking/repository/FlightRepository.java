package com.aerotravel.flightticketbooking.repository;

import com.aerotravel.flightticketbooking.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Passenger, Long> {
}
