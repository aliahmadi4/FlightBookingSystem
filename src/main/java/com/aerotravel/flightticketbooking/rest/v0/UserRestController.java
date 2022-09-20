package com.aerotravel.flightticketbooking.rest.v0;

import com.aerotravel.flightticketbooking.model.User;
import com.aerotravel.flightticketbooking.model.dto.UserDto;
import com.aerotravel.flightticketbooking.services.EntityService;
import com.aerotravel.flightticketbooking.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0/users")
public class UserRestController extends AbstractRestController<User, UserDto> {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected EntityService<User> getService() {
        return userService;
    }

    @Override
    protected UserDto convertToDto(User entity) {
        return modelMapper.map(entity, UserDto.class);
    }

    @Override
    protected User convertToEntity(UserDto entityDto) {
        //TODO(L.E.) Fix Roles issue one day.
        return modelMapper.map(entityDto, User.class);
    }

    @GetMapping("/load/{username}")
    @Operation(summary = "Attempt to get a user by username.")
    public UserDetails findByUsername(@PathVariable String username) {
        return userService.loadUserByUsername(username);
    }
}
