package com.pruu.pombo.model.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

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
    @DisplayName("Should not be able to insert a new user")
    public void testInsert$userWithSameEmail() {
        User userA = new User();
        userA.setName("user A");
        userA.setEmail("a@example.com");
        userA.setCpf("12833057989");
        userRepository.save(userA);

        User savedUser = new User();
        savedUser.setName("name");
        savedUser.setEmail("a@example.com");
        savedUser.setCpf("43251026046");

        assertThatThrownBy(() -> userRepository.save(savedUser)).isInstanceOf(Exception.class);
    }
}
