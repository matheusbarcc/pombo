package com.pruu.pombo.model.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.pruu.pombo.factories.PublicationFactory;
import com.pruu.pombo.factories.UserFactory;
import com.pruu.pombo.model.entity.Publication;
import com.pruu.pombo.model.entity.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PublicationRepositoryTest {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Should not be able to insert publication with content with more than 300 characters")
    public void testInsert$contentMoreThan300Characters() {
        User user = userRepository.save(UserFactory.createUser());
        String content = "a";
        Publication publication = new Publication();
        publication.setUser(user);
        publication.setContent(content.repeat(301));

        assertThatThrownBy(() -> publicationRepository.save(publication)).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Should not be able to insert publication with content with more than 300 characters")
    public void test() {
        User user = userRepository.save(UserFactory.createUser());

        System.out.println(user);
    }
}
