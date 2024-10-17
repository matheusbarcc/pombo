package com.pruu.pombo.model.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pruu.pombo.factories.UserFactory;
import com.pruu.pombo.model.entity.User;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.TransactionSystemException;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should not be able to insert a user with invalid name")
    public void testInsert$nameMoreThan200Characters() {
        String name = "a";
        User savedUser = new User();
        savedUser.setName(name.repeat(201));
        savedUser.setEmail("email@example.com");
        savedUser.setCpf("06512329961");

        assertThatThrownBy(() ->userRepository.save(savedUser)).isInstanceOf(TransactionSystemException.class);
    }

    @Test
    @DisplayName("Should not be able to insert a user with invalid email")
    public void testInsert$userWithInvalidEmail() {
        User savedUser = new User();
        savedUser.setName("name");
        savedUser.setEmail("email");
        savedUser.setCpf("06512329961");

        assertThatThrownBy(() ->userRepository.save(savedUser)).isInstanceOf(TransactionSystemException.class);
    }

    @Test
    @DisplayName("Should not be able to insert a user with invalid CPF")
    public void testInsert$userWithInvalidCpf() {
        User savedUser = new User();
        savedUser.setName("name");
        savedUser.setEmail("email@example.com");
        savedUser.setCpf("111111111");

        assertThatThrownBy(() -> userRepository.save(savedUser)).isInstanceOf(TransactionSystemException.class);
    }
}
