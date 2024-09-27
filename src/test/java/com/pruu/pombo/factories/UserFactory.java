package com.pruu.pombo.factories;

import com.pruu.pombo.model.entity.User;

public class UserFactory {
    public static User createUser() {
        User u = new User();
        u.setName("Name");
        u.setEmail("email@example.com");
        u.setCpf("12833057989");

        return u;
    }
}
