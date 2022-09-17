package com.aerotravel.flightticketbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Aircraft implements IdedEntity{
    @Id
    @GeneratedValue
    private long aircraftId;
    private String manufacturer;
    private String model;
    private Integer numberOfSeats;

    @OneToMany(mappedBy = "aircraft")
    private List<Flight> flights = new ArrayList<>();

    public Aircraft( String manufacturer, String model, Integer numberOfSeats) {
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
    public long getId() {
        return aircraftId;
    }
}
