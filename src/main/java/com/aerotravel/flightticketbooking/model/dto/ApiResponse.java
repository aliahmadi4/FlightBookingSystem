package com.aerotravel.flightticketbooking.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * A generic API response. A way to send a message back.
 */
@Data
@Builder(builderMethodName = "of")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class ApiResponse {
    private int code;
    private String message;
    private Map<String, String> details;
}
