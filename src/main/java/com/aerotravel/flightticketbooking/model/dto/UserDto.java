package com.aerotravel.flightticketbooking.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements IdedEntity {
    private long id;
    private String firstname;
    private String middlename;
    private String lastname;
    private String username;
    private String email;
    private String password;
    @Builder.Default
    private List<String> roleNames = new ArrayList<>();

    @Override
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getId() {
        return id;
    }
}
