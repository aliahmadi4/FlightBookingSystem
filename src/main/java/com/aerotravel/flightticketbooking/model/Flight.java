package com.aerotravel.flightticketbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    @Id
    @GeneratedValue
    private long flightId;
    private String flightNumber;
    @ManyToOne
    private Airport departureAirport;
    @ManyToOne
    private Airport destinationAirport;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate arrivalDate;

    private String departureTime;

    private String arrivalTime;

    private double flightCharge;

    @ManyToOne
    Aircraft aircraft;

    @OneToMany(mappedBy = "flight")
    @Builder.Default
    List<Passenger> passengers = new ArrayList<>();

    public Flight(String flightNumber, Airport departureAirport, Airport destinationAirport,
                  double flightCharge, LocalDate departureDate, LocalDate arrivalDate) {

        this.flightNumber = flightNumber;
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.flightCharge = flightCharge;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightId=" + flightId +
                ", flightNumber='" + flightNumber + '\'' +
                ", departureAirport=" + (departureAirport == null ? null : departureAirport.getAirportCode()) +
                ", destinationAirport=" + (destinationAirport == null ? null : destinationAirport.getAirportCode()) +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", flightCharge=" + flightCharge +
                ", aircraft=" + (aircraft == null ? null : aircraft.getModel()) +
                ", passengers=" + passengers.stream().map(Passenger::getLastName).collect(Collectors.toList()) +
                '}';
    }

    public String toShortString() {
        return "Flight{" +
                "flightId=" + flightId +
                ", flightNumber='" + flightNumber + '\'' +
                ", departureAirport=" + (departureAirport == null ? null : departureAirport.getAirportCode()) +
                ", destinationAirport=" + (destinationAirport == null ? null : destinationAirport.getAirportCode()) +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", flightCharge=" + flightCharge +
                ", aircraft=" + (aircraft == null ? null : aircraft.getModel()) +
                '}';
    }
}
