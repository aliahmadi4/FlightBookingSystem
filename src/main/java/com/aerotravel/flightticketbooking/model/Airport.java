package com.aerotravel.flightticketbooking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Airport {
    @Id
    @GeneratedValue
    private int airportId;
    private String airportCode;
    private String airportName;
    private String city;
    private String state;
    private String country;


    @OneToMany(mappedBy = "departureAirport")
    List<Flight> flights = new ArrayList<Flight>();

    public Airport() {
    }

    public Airport(String airportCode, String airportName, String city, String state, String country) {
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public Integer getAirportId() {
        return airportId;
    }

    public void setAirportId(Integer airportId) {
        this.airportId = airportId;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAirportId(int airportId) {
        this.airportId = airportId;
    }


    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

}
