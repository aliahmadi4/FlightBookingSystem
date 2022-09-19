package com.aerotravel.flightticketbooking.services;

import com.aerotravel.flightticketbooking.model.Passenger;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PassengerService {
    Page<Passenger> getAllPassengersPaged(int pageNum);

    List<Passenger> getAllPassengers();

    Passenger getPassengerById(Long passengerId);

    List<Passenger> getAllById(List<Long> passengerIds);

    Passenger savePassenger(Passenger passenger);

    void deletePassengerById(Long passengerId);
}
