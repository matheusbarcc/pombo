package com.pruu.pombo.model.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.pruu.pombo.factories.UserFactory;
import com.pruu.pombo.model.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    User user;

    @BeforeEach
    public void setUp() {
        user = userRepository.save(UserFactory.createUser());
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Should be able to insert a new user")
    public void testInsert$success() {
        User user = new User();
        user.setName("name");
        user.setEmail("email@email.com");
        user.setCpf("43251026046");

        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("name");
    }

    @Test
    @DisplayName("Should not be able to insert a user with same email")
    public void testInsert$userWithSameEmail() {
        User savedUser = new User();
        savedUser.setName("name");
        savedUser.setEmail(user.getEmail());
        savedUser.setCpf("43251026046");

        assertThatThrownBy(() -> userRepository.save(savedUser)).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Should not be able to insert a user with same CPF")
    public void testInsert$userWithSameCpf() {
        User savedUser = new User();
        savedUser.setName("name");
        savedUser.setEmail("email@email.com");
        savedUser.setCpf(user.getCpf());

        assertThatThrownBy(() -> userRepository.save(savedUser)).isInstanceOf(Exception.class);
    }
}
