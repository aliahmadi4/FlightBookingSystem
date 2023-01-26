package com.aerotravel.flightticketbooking.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightDto implements IdedEntity {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long flightId;
    @Size(max = 30, message = "flightNumber - Not longer than 30 characters please!")
    @NotBlank
    private String flightNumber;
    @Size(max = 5, message = "departureAirportCode - Not longer than 5 please!")
    @NotBlank
    private String departureAirportCode;
    @Size(max = 5, message = "destinationAirportCode - Not longer than 5 please!")
    @NotBlank
    private String destinationAirportCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private LocalDate departureDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private LocalDate arrivalDate;
    @NotBlank
    private String departureTime;
    @NotBlank
    private String arrivalTime;
    @PositiveOrZero(message = "flightCharge - Shall be positive!")
    @Max(value = 999999999, message = "That was a bit too much.")
    private double flightCharge;
    private long aircraftId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Builder.Default
    private List<Long> passengerIds = new ArrayList<>();

    @Override
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getId() {
        return flightId;
    }

    @Override
    public void setId(long id) {
        this.flightId = id;
    }
}
