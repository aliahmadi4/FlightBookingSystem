package com.aerotravel.flightticketbooking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AircraftDto implements IdedEntity {
    private long aircraftId;
    private String manufacturer;
    private String model;
    private Integer numberOfSeats;
    @Builder.Default
    private List<Long> flightIds = new ArrayList<>();

    @Override
    public long getId() {
        return aircraftId;
    }
}
