package com.aerotravel.flightticketbooking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder(builderMethodName = "internalBuilder")
@AllArgsConstructor
@NoArgsConstructor
public class FlightDto implements IdedEntity {
    private long flightId;
    private String flightNumber;
    private String departureAirportCode;
    private String destinationAirportCode;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private String departureTime;
    private String arrivalTime;
    private double flightCharge;
    private long aircraftId;
    private List<Long> passengerIds = new ArrayList<>();

    @Override
    public long getId() {
        return flightId;
    }

    public static FlightDtoBuilder builder() {
        return internalBuilder().passengerIds(new ArrayList<>());
    }
}
