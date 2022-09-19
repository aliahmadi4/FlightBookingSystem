package com.aerotravel.flightticketbooking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Override
    public long getId() {
        return airportId;
    }
}
