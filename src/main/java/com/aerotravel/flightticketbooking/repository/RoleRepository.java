package com.aerotravel.flightticketbooking.repository;

import com.aerotravel.flightticketbooking.model.Role;
import com.aerotravel.flightticketbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<User> findByName(String rolename);

}
