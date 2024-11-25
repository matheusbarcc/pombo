package com.pruu.pombo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.pruu.pombo.utils.RSAEncoder;
import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.factories.ComplaintFactory;
import com.pruu.pombo.factories.PublicationFactory;
import com.pruu.pombo.factories.UserFactory;
import com.pruu.pombo.model.dto.PublicationDTO;
import com.pruu.pombo.model.entity.Complaint;
import com.pruu.pombo.model.entity.Publication;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.repository.PublicationRepository;
import com.pruu.pombo.model.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
public class PublicationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PublicationRepository publicationRepository;

    @Mock
    private RSAEncoder rsaEncoder;

    @InjectMocks
    private PublicationService publicationService;

    User user;

    @BeforeEach
    void setUp() {
        user = UserFactory.createUser();
        user.setId("user-01");
        when(userRepository.findById("user-01")).thenReturn(Optional.of(user));
    }

    @AfterEach
    public void tearDown() {
        publicationRepository.deleteAll();
        userRepository.deleteAll();
    }

    // CREATE TESTS

    @Test
    @DisplayName("Should be able to create a new publication")
    public void testCreate$success() throws PomboException {
        Publication publication = PublicationFactory.createPublication(user);
        System.out.println(publication);

        when(publicationRepository.saveAndFlush(publication)).thenReturn(publication);
        Publication result = publicationService.create(publication);

        assertThat(result).isNotNull();
        assertThat(result.getUser()).isEqualTo(user);
    }

    // LIKE TESTS

    /*
        the liking feature works like a switch, when called once it will like the publication
        if called again with the same publication and user id it will dislike it, for this
        reason it was created only one test for both cases
    */
    @Test
    @DisplayName("Should be able to like/dislike a publication")
    public void testLikeDislike$success() throws PomboException {
        Publication publication = PublicationFactory.createPublication(user);
        publication.setId("publication-01");

        when(publicationRepository.findById("publication-01")).thenReturn(Optional.of(publication));

        when(publicationRepository.save(publication)).thenReturn(publication);
        publicationService.like("user-01", "publication-01");

        assertThat(publication.getLikes()).hasSize(1);
        assertThat(publication.getLikes().get(0)).isEqualTo(user);

        publicationService.like("user-01", "publication-01");

        assertThat(publication.getLikes()).hasSize(0);
    }


    @Test
    @DisplayName("Should not be able to like a invalid publication")
    public void testLike$invalidPublication() {
        assertThatThrownBy(() -> publicationService.like("user-01", "publication-01"))
                .isInstanceOf(PomboException.class).hasMessageContaining("Publicação não encontrada");
    }
}
