package com.pruu.pombo.factories;

import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.utils.CpfGenerator;

import java.util.UUID;

public class UserFactory {
    public static User createUser() {
        User u = new User();
        u.setName("Name");
        u.setEmail(UUID.randomUUID() + "@example.com");
        u.setCpf(CpfGenerator.generateValidCPF());
        u.setPassword("123");
        return u;
    }
}
