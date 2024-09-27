package com.pruu.pombo.model.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.pruu.pombo.factories.PublicationFactory;
import com.pruu.pombo.factories.UserFactory;
import com.pruu.pombo.model.entity.Complaint;
import com.pruu.pombo.model.entity.Publication;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.enums.Reason;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ComplaintRepositoryTest {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserRepository userRepository;

    User user;
    Publication publication;

    @BeforeEach
    public void setUp() {
        user = userRepository.save(UserFactory.createUser());
        publication = publicationRepository.save(PublicationFactory.createPublication(user));
    }

    @AfterEach
    public void tearDown() {
        complaintRepository.deleteAll();
        publicationRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Should be able to insert a new complaint")
    public void testInsert$success() {
        Complaint complaint = new Complaint();
        complaint.setUser(user);
        complaint.setPublication(publication);
        complaint.setReason(Reason.SCAM);

        Complaint savedComplaint = complaintRepository.save(complaint);

        assertThat(savedComplaint.getId()).isNotNull();
        assertThat(savedComplaint.getUser().getId()).isEqualTo(user.getId());
        assertThat(savedComplaint.getPublication().getId()).isEqualTo(publication.getId());
        assertThat(savedComplaint.getReason()).isEqualTo(Reason.SCAM);
    }

    @Test
    @DisplayName("Should not be able to insert a complaint without reason")
    public void testInsert$complaintWithoutReason() {
        Complaint complaint = new Complaint();
        complaint.setUser(user);
        complaint.setPublication(publication);

        assertThatThrownBy(() -> complaintRepository.save(complaint)).isInstanceOf(Exception.class);
    }
}
