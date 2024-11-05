package com.pruu.pombo.model.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.pruu.pombo.factories.UserFactory;
import com.pruu.pombo.model.entity.User;
import jakarta.validation.ConstraintViolationException;
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
    @DisplayName("Should not be able to insert a user with invalid name")
    public void testInsert$nameMoreThan200Characters() {
        String name = "a";
        User user = UserFactory.createUser();
        user.setName(name.repeat(201));

        assertThatThrownBy(() -> userRepository.saveAndFlush(user)).isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("O nome deve conter no máximo 200 caracteres");
    }

    @Test
    @DisplayName("Should not be able to insert a user with invalid email")
    public void testInsert$userWithInvalidEmail() {
        User savedUser = UserFactory.createUser();
        savedUser.setEmail("aaaaaaa");

        assertThatThrownBy(() ->userRepository.saveAndFlush(savedUser)).isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("O email deve ser válido");
    }

    @Test
    @DisplayName("Should not be able to insert a user with invalid CPF")
    public void testInsert$userWithInvalidCpf() {
        User savedUser = UserFactory.createUser();
        savedUser.setCpf("11111111111");

        assertThatThrownBy(() -> userRepository.saveAndFlush(savedUser)).isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("O CPF deve ser válido");
    }

    @Test
    @DisplayName("Should not be able to insert a user without password")
    public void testInsert$userWithoutPassword() {
        User savedUser = UserFactory.createUser();
        savedUser.setPassword(null);

        assertThatThrownBy(() -> userRepository.saveAndFlush(savedUser)).isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("A senha não deve estar em branco.");
    }
}
