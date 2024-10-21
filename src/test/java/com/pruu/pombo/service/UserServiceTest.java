package com.pruu.pombo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.factories.PublicationFactory;
import com.pruu.pombo.factories.UserFactory;
import com.pruu.pombo.model.entity.Publication;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should be able to create a new user")
    public void testCreate$success() throws PomboException {
        User newUser = new User();
        newUser.setName("New user");
        newUser.setEmail("email@email.com");
        newUser.setCpf("43251026046");

        when(userRepository.save(newUser)).thenReturn(newUser);
        User result = userService.create(newUser);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(newUser.getName());
        assertThat(result.getEmail()).isEqualTo(newUser.getEmail());
        assertThat(result.getCpf()).isEqualTo(newUser.getCpf());
    }

    @Test
    @DisplayName("Should not be able to create a user with same email")
    public void testCreate$UserWithSameEmail() throws PomboException {
        User user = UserFactory.createUser();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        User newUser = new User();
        newUser.setName("New user");
        newUser.setEmail(user.getEmail());
        newUser.setCpf("43251026046");

        when(userRepository.save(newUser)).thenReturn(newUser);

        assertThatThrownBy(() -> userService.create(newUser)).isInstanceOf(PomboException.class)
                .hasMessageContaining("Email já cadastrado");
    }

    @Test
    @DisplayName("Should not be able to create a user with same cpf")
    public void testCreate$UserWithSameCpf() throws PomboException {
        User user = UserFactory.createUser();
        when(userRepository.findByCpf(user.getCpf())).thenReturn(user);

        User newUser = new User();
        newUser.setName("New user");
        newUser.setEmail("email@email.com");
        newUser.setCpf(user.getCpf());

        when(userRepository.save(newUser)).thenReturn(newUser);

        assertThatThrownBy(() -> userService.create(newUser)).isInstanceOf(PomboException.class)
                .hasMessageContaining("CPF já cadastrado");
    }

    @Test
    @DisplayName("Should standardize CPF upon user creation")
    public void testCpfStandardization$success() throws PomboException {
        User newUser = new User();
        newUser.setCpf("432.510.260-46");

        when(userRepository.save(newUser)).thenReturn(newUser);
        User result = userService.create(newUser);

        assertThat(result.getCpf()).isEqualTo("43251026046");
    }

    @Test
    @DisplayName("Should be able to update a new user")
    public void testUpdate$success() throws PomboException {
        User newUser = new User();
        newUser.setName("New user");
        newUser.setEmail("email@email.com");
        newUser.setCpf("43251026046");

        when(userRepository.save(newUser)).thenReturn(newUser);
        User savedNewUser = userService.create(newUser);

        assertThat(savedNewUser.getName()).isEqualTo("New user");
        assertThat(savedNewUser.getEmail()).isEqualTo("email@email.com");

        newUser.setName("Updated new user");
        newUser.setEmail("updatedEmail@email.com");

        User result = userService.update(newUser);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated new user");
        assertThat(result.getEmail()).isEqualTo("updatedEmail@email.com");
    }

    @Test
    @DisplayName("Should be able to find a user by id")
    public void testFindById$success() {
        User newUser = new User();
        newUser.setName("New user");
        newUser.setEmail("email@email.com");
        newUser.setCpf("43251026046");

        when(userRepository.findById("user-01")).thenReturn(Optional.of(newUser));
        User result = userService.findById("user-01");

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(newUser.getId());
    }
}
