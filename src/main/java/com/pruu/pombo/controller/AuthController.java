package com.pruu.pombo.controller;

import com.pruu.pombo.auth.AuthService;
import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.enums.Role;
import com.pruu.pombo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("authenticate")
    public String authenticate(Authentication authentication) {
        return authService.authenticate(authentication);
    }

    @Operation(summary = "Creates a new user",
            description = "Creates a new user, the request must receive at least name, email and CPF in the body",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A new user has been successfully created"),
                    @ApiResponse(responseCode = "400", description = "Wrong or forgotten information on the body"),
                    @ApiResponse(responseCode = "409", description = "Email or CPF already registered")
            })
    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User create(@Valid @RequestBody User user) throws PomboException {

        String encryptedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encryptedPassword);

        if(user.getRole().equals(Role.ADMIN)) {
            user.setRole(Role.ADMIN);
        }

        return userService.create(user);
    }
}
