package com.aerotravel.flightticketbooking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "passportNumber")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long passengerId;
    @NotBlank
    @Size(max = 300)
    private String firstName;
    @NotBlank
    @Size(max = 300)
    private String lastName;
    @NotBlank
    @Size(max = 30)
    private String phoneNumber;
    @NotBlank
    @Size(max = 30)
    private String passportNumber;

    @Email
    @Size(max = 300)
    private String email;

    @Size(max = 300)
    private String address;

    @ManyToOne
    @JsonBackReference("flight-passengers")
    private Flight flight;

    public Passenger(String firstName, String lastName, String phoneNumber, String passportNumber, String email, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.passportNumber = passportNumber;
        this.email = email;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "passengerId=" + passengerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", flight=" + (null == flight ? null : flight.getFlightNumber()) +
                '}';
    }
}
