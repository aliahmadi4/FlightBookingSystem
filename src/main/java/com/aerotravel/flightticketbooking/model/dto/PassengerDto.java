package com.aerotravel.flightticketbooking.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDto implements IdedEntity {
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
    @Size(max = 30, min = 1)
    private String passportNumber;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(max = 300)
    private String address;
    private long flightId;

    @Override
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getId() {
        return passengerId;
    }

    @Override
    public void setId(long id) {
        this.passengerId = id;
    }
}
