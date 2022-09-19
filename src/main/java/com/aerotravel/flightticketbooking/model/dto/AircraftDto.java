package com.aerotravel.flightticketbooking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AircraftDto implements IdedEntity{
    private long aircraftId;
    private String manufacturer;
    private String model;
    private Integer numberOfSeats;
    private List<Long> flightIds;

    @Override
    public long getId() {
        return aircraftId;
    }
}
