package com.pruu.pombo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.factories.ComplaintFactory;
import com.pruu.pombo.factories.PublicationFactory;
import com.pruu.pombo.factories.UserFactory;
import com.pruu.pombo.model.dto.PublicationDTO;
import com.pruu.pombo.model.entity.Complaint;
import com.pruu.pombo.model.entity.Publication;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.enums.Role;
import com.pruu.pombo.model.repository.ComplaintRepository;
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
    private ComplaintRepository complaintRepository;

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

        when(publicationRepository.save(publication)).thenReturn(publication);
        Publication result = publicationService.create(publication);

        assertThat(result).isNotNull();
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getContent()).isEqualTo(publication.getContent());
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
    @DisplayName("Should be able to fetch likes from a specific publication")
    public void testFetchLikes$success() throws PomboException {
        User user1 = UserFactory.createUser();
        user1.setId("user-01");
        User user2 = UserFactory.createUser();
        user1.setId("user-02");

        List<User> likes = new ArrayList<>();
        likes.add(user1);
        likes.add(user2);

        Publication publication = PublicationFactory.createPublication(user1);
        publication.setId("publication-01");
        publication.setLikes(likes);

        when(publicationRepository.findById("publication-01")).thenReturn(Optional.of(publication));
        List<User> result = publicationService.fetchPublicationLikes("publication-01");

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo(user1);
        assertThat(result.get(1)).isEqualTo(user2);
    }


    @Test
    @DisplayName("Should not be able to like a invalid publication")
    public void testLike$invalidPublication() {
        assertThatThrownBy(() -> publicationService.like("user-01", "publication-01"))
                .isInstanceOf(PomboException.class).hasMessageContaining("Publicação não encontrada");
    }

    // COMPLAINTS TEST

    @Test
    @DisplayName("Should be able to fetch complaints from a specific publication")
    public void testFetchComplaints$success() throws PomboException {
        Publication publication = PublicationFactory.createPublication(user);
        publication.setId("publication-01");

        Complaint complaint1 = ComplaintFactory.createComplaint(user, publication);
        Complaint complaint2 = ComplaintFactory.createComplaint(user, publication);

        List<Complaint> complaints = new ArrayList<>();
        complaints.add(complaint1);
        complaints.add(complaint2);

        publication.setComplaints(complaints);

        when(publicationRepository.findById("publication-01")).thenReturn(Optional.of(publication));
        List<Complaint> result = publicationService.fetchPublicationComplaints("publication-01");

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo(complaint1);
        assertThat(result.get(1)).isEqualTo(complaint2);
    }

    // DTO TESTS

    @Test
    @DisplayName("Should be able to fetch publication DTOs")
    public void testFetchDTOs$success() throws PomboException {
        User user1 = UserFactory.createUser();
        user1.setId("user-01");
        User user2 = UserFactory.createUser();
        user2.setId("user-02");

        List<User> likes = new ArrayList<>();
        likes.add(user1);
        likes.add(user2);

        Publication publication1 = PublicationFactory.createPublication(user1);
        publication1.setId("publication-01");
        Publication publication2 = PublicationFactory.createPublication(user2);
        publication2.setId("publication-02");
        publication2.setLikes(likes);

        List<Publication> publications = new ArrayList<>();
        publications.add(publication1);
        publications.add(publication2);

        Complaint complaint1 = ComplaintFactory.createComplaint(user2, publication1);
        complaint1.setId("complaint-01");

        List<Complaint> complaints = new ArrayList<>();
        complaints.add(complaint1);

        publication1.setComplaints(complaints);

        when(publicationRepository.findById("publication-01")).thenReturn(Optional.of(publication1));
        when(publicationRepository.findById("publication-02")).thenReturn(Optional.of(publication2));

        when(publicationService.findAll()).thenReturn(publications);

        List<PublicationDTO> result = publicationService.fetchDTOs();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getComplaintAmount()).isEqualTo(1);
        assertThat(result.get(1).getLikeAmount()).isEqualTo(2);
    }
}
