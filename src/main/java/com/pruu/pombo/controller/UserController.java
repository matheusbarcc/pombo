package com.pruu.pombo.controller;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.selector.UserSelector;
import com.pruu.pombo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

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

    @Operation(summary = "Updates a user",
            description = "Updates a user, the request must receive at least the user id that is being updated",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The user has been successfully updated"),
                    @ApiResponse(responseCode = "400", description = "Wrong or forgotten information on the body"),
                    @ApiResponse(responseCode = "409", description = "Email or CPF already registered")
            })
    @PutMapping
    public User update(@Valid @RequestBody User user) throws PomboException {
        return userService.update(user);
    }

    @Operation(summary = "Finds a specific user by their id",
            description = "Finds a specific user by their id, the id must be passed through the path of the request",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The user information is returned"),
            })
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable String id) {
        return ResponseEntity.ok(userService.findById(id));
    }

//    @DeleteMapping("/{id}")
//    public boolean deleteById(@PathVariable String id) {
//        // TODO validate permission
//        return userService.delete(id);
//    }
}
