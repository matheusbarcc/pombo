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

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should not be able to insert a user with invalid email")
    public void testInsert$userWithInvalidEmail() {
        User savedUser = new User();
        savedUser.setName("Edson Arantes");
        savedUser.setEmail("email");
        savedUser.setCpf("06512329961");

        try {
            userRepository.save(savedUser);
        } catch (Exception e){
            System.out.println(e.getMessage());
            assertTrue(e.getMessage().contains("email"));
        }

        assertThatThrownBy(() ->
                userRepository.save(savedUser))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Should not be able to insert a user with invalid CPF")
    public void testInsert$userWithInvalidCpf() {
        User savedUser = new User();
        savedUser.setName("name");
        savedUser.setEmail("email@email.com");
        savedUser.setCpf("111111111");

        assertThatThrownBy(() -> userRepository.save(savedUser)).isInstanceOf(Exception.class)
                .hasMessageContaining("O CPF deve ser válido.");
    }
}
