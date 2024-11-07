package com.pruu.pombo.controller;

import com.pruu.pombo.auth.AuthService;
import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.enums.Role;
import com.pruu.pombo.model.selector.UserSelector;
import com.pruu.pombo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Operation(summary = "Updates a user",
            description = "Updates a user, the request must receive at least the user id that is being updated",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The user has been successfully updated"),
                    @ApiResponse(responseCode = "400", description = "Wrong or forgotten information on the body"),
                    @ApiResponse(responseCode = "409", description = "Email or CPF already registered")
            })
    @PutMapping
    public User update(@Valid @RequestBody User user) throws PomboException {
        User subject = authService.getAuthenticatedUser();

        user.setId(subject.getId());

        return userService.update(user);
    }

    @Operation(summary = "Fetches users with filters",
            description = "Fetches a group of users based on a filter passed through the body, pagination also included",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A list of users is returned based on the filters and page"),
                    @ApiResponse(responseCode = "400", description = "Wrong or forgotten information on the body")
            })
    @PostMapping("/filter")
    public List<User> fetchWithFilter(@RequestBody UserSelector selector) {
        return userService.fetchWithFilter(selector);
    }

    @Operation(summary = "Finds a specific user by their id",
            description = "Finds a specific user by their id, the id must be passed through the path of the request",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The user information is returned"),
            })
    @GetMapping("/{id}")
    public User findById(@PathVariable String id) {
        return userService.findById(id);
    }

//    @DeleteMapping("/{userId}")
//    public ResponseEntity<Void> delete(@PathVariable String userId) throws PomboException {
//        User subject = authService.getAuthenticatedUser();
//
//        if (subject.getRole() == Role.USER) {
//            if(subject.getId().equals(userId)) {
//                userService.delete(userId);
//                return ResponseEntity.ok().build();
//            } else {
//                throw new PomboException("Você não pode excluir a conta de outros usuários.", HttpStatus.UNAUTHORIZED);
//            }
//        } else {
//            throw new PomboException("Administradores não podem excluir suas contas.", HttpStatus.UNAUTHORIZED);
//        }
//    }
}
