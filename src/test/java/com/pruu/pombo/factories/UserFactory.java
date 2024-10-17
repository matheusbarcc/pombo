package com.pruu.pombo.factories;

import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.utils.CPFUtils;

import java.util.UUID;

public class UserFactory {
    public static User createUser() {
        User u = new User();
        u.setName("Name");
        u.setEmail(UUID.randomUUID() + "@example.com");
        u.setCpf(CPFUtils.generateValidCPF());
        return u;
    }
}
