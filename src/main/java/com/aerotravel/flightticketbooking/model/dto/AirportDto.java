package com.aerotravel.flightticketbooking.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirportDto implements IdedEntity {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long airportId;
    @Size(max = 5, min = 2)
    private String airportCode;
    @Size(max = 300, min = 3)
    private String airportName;
    @Size(max = 300)
    private String city;
    @Size(max = 300)
    private String state;
    @Size(max = 300)
    private String country;
    @Builder.Default
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> flightIds = new ArrayList<>();

    @Override
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getId() {
        return airportId;
    }

    @Override
    public void setId(long id) {
        this.airportId = id;
    }
}
