package com.aerotravel.flightticketbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.aerotravel.flightticketbooking.repository")
@EntityScan("com.aerotravel.flightticketbooking.model")
@SpringBootApplication
public class FlightticketbookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightticketbookingApplication.class, args);
    }
}