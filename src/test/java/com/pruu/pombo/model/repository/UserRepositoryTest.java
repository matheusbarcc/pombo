package com.pruu.pombo.model.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.pruu.pombo.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();

        List<User> users = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            User user = new User();
            user.setName("name " + i);
            user.setEmail("email" + i + "@email.com");
            user.setCpf("1234567890" + i);
            users.add(user);
        }

        userRepository.saveAll(users);
    }

    @Test
    public void testUserInsert() {
        User user = new User();
        user.setName("name");
        user.setEmail("email@email.com");
        user.setCpf("12345678901");

        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isNotNull();
    }
}
