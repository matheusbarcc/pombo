package com.pruu.pombo.service;

import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }
}
