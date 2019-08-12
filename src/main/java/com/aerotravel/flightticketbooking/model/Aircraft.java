package com.aerotravel.flightticketbooking.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Aircraft {
    @Id
    @GeneratedValue
    private long aircraftId;
    private String manufacturer;
    private String model;
    private Integer numberOfSeats;

    @ManyToOne
    private Airport airport;

    @OneToMany(mappedBy = "aircraft")
    private List<Flight> flights = new ArrayList<>();


    public Aircraft() {
    }

    public Aircraft(long aircraftId, String manufacturer, String model, Integer numberOfSeats) {
        this.aircraftId = aircraftId;
        this.manufacturer = manufacturer;
        this.model = model;
        this.numberOfSeats = numberOfSeats;
    }

    public long getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(long aircraftId) {
        this.aircraftId = aircraftId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
}
