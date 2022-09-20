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
public class AirportDto implements IdedEntity {
    private long airportId;
    private String airportCode;
    private String airportName;
    private String city;
    private String state;
    private String country;
    @Builder.Default
    private List<Long> flightIds = new ArrayList<>();

    @Override
    public long getId() {
        return airportId;
    }
}
