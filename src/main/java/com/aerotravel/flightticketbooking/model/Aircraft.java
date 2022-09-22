package com.aerotravel.flightticketbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Aircraft {
    @Id
    @GeneratedValue
    private long aircraftId;
    @Size(max = 300)
    private String manufacturer;
    @Size(max = 300)
    private String model;
    @Max(value = 1000, message = "* Number of seats cannot be too big.")
    @Min(value = 1, message = "* Number of seats cannot be too small.")
    private Integer numberOfSeats;
    @OneToMany(mappedBy = "aircraft")
    @Builder.Default
    private List<Flight> flights = new ArrayList<>();

    public Aircraft(String manufacturer, String model, Integer numberOfSeats) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.numberOfSeats = numberOfSeats;
    }

    public Aircraft(long id, String manufacturer, String model, Integer numberOfSeats) {
        this.aircraftId = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public String toString() {
        return "Aircraft{" +
                "aircraftId=" + aircraftId +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", flights=" + flights.stream()
                .filter(Objects::nonNull).map(Flight::getFlightNumber).collect(Collectors.toList()) +
                '}';
    }

    public String toShortString() {
        return "Aircraft{" +
                "aircraftId=" + aircraftId +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                '}';
    }
}
