package com.aerotravel.flightticketbooking.services;

import com.aerotravel.flightticketbooking.exception.EntityNotFoundException;
import com.aerotravel.flightticketbooking.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService, EntityService<User> {
    default EntityNotFoundException buildEntityNotFoundException(long id) {
        return buildEntityNotFoundException("User", id);
    }
}
