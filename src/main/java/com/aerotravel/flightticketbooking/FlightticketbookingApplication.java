package com.aerotravel.flightticketbooking;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.aerotravel.flightticketbooking.repository")
@EntityScan("com.aerotravel.flightticketbooking.model")
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Flight ticket booking API", version = "00.00",
        contact = @Contact(name = "FTB API Support",
                url = "http://ya.ru",
                email = "4ern@dyr.a")))
public class FlightticketbookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightticketbookingApplication.class, args);
    }
}