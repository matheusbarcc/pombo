package com.pruu.pombo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.pruu.pombo.factories.UserFactory;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

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
    @DisplayName("Should be able to create a new user")
    public void testCreate$success() {
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
    @DisplayName("Should be able to update a new user")
    public void testUpdate$success() {
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

//    @Test
//    @DisplayName("Should be able to fetch users using filters")
//    public void testFetchWithFilter$success() {
////        User newUser = new User();
////        newUser.setId("user-01");
////
////        when(userRepository.findById("user-01")).thenReturn(Optional.of(newUser));
////        User result = userService.findById("user-01");
////
////        assertThat(result).isNotNull();
////        assertThat(result.getId()).isEqualTo(newUser.getId());
//    }
}
