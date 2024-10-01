package com.pruu.pombo.model.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.pruu.pombo.factories.PublicationFactory;
import com.pruu.pombo.factories.UserFactory;
import com.pruu.pombo.model.entity.Publication;
import com.pruu.pombo.model.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class PublicationRepositoryTest {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserRepository userRepository;

    User user;

    @BeforeEach
    public void setUp() {
        user = userRepository.save(UserFactory.createUser());
    }

    @AfterEach
    public void tearDown() {
        publicationRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Should be able to insert a new publication")
    public void testInsert$success() {
        Publication publication = new Publication();
        publication.setUser(user);
        publication.setContent("New content");

        Publication savedPublication = publicationRepository.save(publication);

        assertThat(savedPublication.getId()).isNotNull();
        assertThat(savedPublication.getUser().getId()).isEqualTo(user.getId());
        assertThat(savedPublication.getContent()).isEqualTo("New content");
    }

    @Test
    @DisplayName("Should not be able to insert publication with content bigger than 300 characters")
    public void testInsert$contentMoreThan300Characters() {
        Publication publication = new Publication();
        String content = "a";
        publication.setUser(user);
        publication.setContent(content.repeat(301));

        assertThatThrownBy(() -> publicationRepository.save(publication)).isInstanceOf(Exception.class);
    }
}
