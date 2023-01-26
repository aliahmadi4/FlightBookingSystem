package com.aerotravel.flightticketbooking.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss:nn Z")
    private ZonedDateTime timestamp;

    private String status;

    // General message.
    private String message;

    // Details if any.
    private List<String> details;

    public ErrorResponse(String status, String message, List<String> details) {
        this.status = status;
        this.message = message;
        this.details = details;

        this.timestamp = ZonedDateTime.now(ZoneId.systemDefault());
    }
}
