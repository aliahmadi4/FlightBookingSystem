package com.aerotravel.flightticketbooking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder(builderMethodName = "internalBuilder")
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements IdedEntity {
    private Long id;
    private String firstname;
    private String middlename;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private List<String> roleNames = new ArrayList<>();

    public static UserDtoBuilder builder() {
        return internalBuilder().roleNames(new ArrayList<>());
    }

    @Override
    public long getId() {
        return id;
    }
}
