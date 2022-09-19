package com.aerotravel.flightticketbooking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDto implements IdedEntity {
    private long passengerId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String passportNumber;
    private String email;
    private String address;
    private long flightId;

    @Override
    public long getId() {
        return passengerId;
    }
}
