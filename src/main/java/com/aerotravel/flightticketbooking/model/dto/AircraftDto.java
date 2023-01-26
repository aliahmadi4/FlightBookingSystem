package com.aerotravel.flightticketbooking.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AircraftDto implements IdedEntity {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long aircraftId;
    @Size(max = 300)
    private String manufacturer;
    @Size(max = 300)
    private String model;
    @Max(value = 1000, message = "* Number of seats cannot be too big.")
    @Min(value = 1, message = "* Number of seats cannot be too small.")
    private Integer numberOfSeats;
    @Builder.Default
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> flightIds = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Override
    public long getId() {
        return aircraftId;
    }

    @Override
    public void setId(long id) {
        this.aircraftId = id;
    }
}
