package com.pruu.pombo.controller;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User create(@Valid @RequestBody User user) throws PomboException {
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) throws PomboException {
        return userService.update(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable String id) {
        return ResponseEntity.ok(userService.findById(id));
    }
}
