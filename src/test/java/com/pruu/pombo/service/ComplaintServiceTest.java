package com.pruu.pombo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.factories.ComplaintFactory;
import com.pruu.pombo.factories.PublicationFactory;
import com.pruu.pombo.factories.UserFactory;
import com.pruu.pombo.model.dto.ComplaintDTO;
import com.pruu.pombo.model.entity.Complaint;
import com.pruu.pombo.model.entity.Publication;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.enums.ComplaintStatus;
import com.pruu.pombo.model.enums.Reason;
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
class ComplaintServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PublicationRepository publicationRepository;

    @Mock
    private ComplaintRepository complaintRepository;

    @InjectMocks
    private ComplaintService complaintService;


    User user;
    User admin;
    Publication publication;

    @BeforeEach
    void setUp() {
        user = UserFactory.createUser();
        user.setId("user-01");
        when(userRepository.findById("user-01")).thenReturn(Optional.of(user));

        admin = UserFactory.createUser();
        admin.setRole(Role.ADMIN);
        admin.setId("admin-01");
        when(userRepository.findById("admin-01")).thenReturn(Optional.of(admin));

        publication = PublicationFactory.createPublication(user);
        publication.setId("publication-01");
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
        publicationRepository.deleteAll();
        complaintRepository.deleteAll();
    }

    // CREATE TESTS

    @Test
    @DisplayName("Should be able to create a complaint")
    public void testCreate$success() throws PomboException {
        Complaint complaint = ComplaintFactory.createComplaint(user, publication);

        when(complaintRepository.save(complaint)).thenReturn(complaint);
        Complaint result = complaintService.create(complaint);

        assertThat(result).isNotNull();
        assertThat(result.getUser().getId()).isEqualTo("user-01");
        assertThat(result.getReason()).isEqualTo(Reason.SCAM);
    }


    @Test
    @DisplayName("Should not be able to create a complaint with invalid user")
    public void testCreate$invalidUser() {
        User user2 = UserFactory.createUser();

        Complaint complaint = ComplaintFactory.createComplaint(user2, publication);

        assertThatThrownBy(() -> complaintService.create(complaint)).isInstanceOf(PomboException.class)
                .hasMessageContaining("Usuário inválido");
    }

    @Test
    @DisplayName("Should not be able to create a complaint with invalid reason")
    public void testCreate$invalidReason() {
        Complaint complaint = ComplaintFactory.createComplaint(user, publication);
        complaint.setReason(null);

        assertThatThrownBy(() -> complaintService.create(complaint)).isInstanceOf(PomboException.class)
                .hasMessageContaining("Motivo inválido");
    }

    // UPDATE TESTS

    /*
        the status updating feature works like a switch, if the status is PENDING
        and the method is called it will change the status to ANALYSED, if called
        again with the same complaint id it will change to PENDING, for this
        reason it was created only one test for both cases
    */
    @Test
    @DisplayName("Should be able to update the status from a complaint")
    public void testUpdateStatus$success() throws PomboException {
        Complaint complaint = ComplaintFactory.createComplaint(user, publication);
        complaint.setId("complaint-01");

        when(complaintRepository.findById("complaint-01")).thenReturn(Optional.of(complaint));
        complaintService.updateStatus("admin-01", "complaint-01");

        assertThat(complaint.getStatus()).isEqualTo(ComplaintStatus.ANALYSED);

        complaintService.updateStatus("admin-01", "complaint-01");

        assertThat(complaint.getStatus()).isEqualTo(ComplaintStatus.PENDING);
    }

    @Test
    @DisplayName("Should not be able to update status without a admin role")
    public void testUpdateStatus$withoutAdminRole() {
        Complaint complaint = ComplaintFactory.createComplaint(user, publication);
        complaint.setId("complaint-01");

        when(complaintRepository.findById("complaint-01")).thenReturn(Optional.of(complaint));

        assertThatThrownBy(() -> complaintService.updateStatus("user-01", "complaint-01"))
                .isInstanceOf(PomboException.class).hasMessageContaining("Usuário não autorizado");
    }

    // DTO TESTS

    @Test
    @DisplayName("Should be able to find DTO by publication id")
    public void testFindDTO$success() throws PomboException {
        Complaint complaint1 = ComplaintFactory.createComplaint(user, publication);
        complaint1.setId("complaint-01");
        complaint1.setStatus(ComplaintStatus.ANALYSED);
        Complaint complaint2 = ComplaintFactory.createComplaint(user, publication);
        complaint2.setId("complaint-02");

        List<Complaint> complaints = new ArrayList<>();
        complaints.add(complaint1);
        complaints.add(complaint2);

        when(complaintRepository.findByPublicationId("publication-01")).thenReturn(complaints);
        ComplaintDTO result = complaintService.findDTOByPublicationId("admin-01", "publication-01");

        assertThat(result).isNotNull();
        assertThat(result.getComplaintAmount()).isEqualTo(2);
        assertThat(result.getPendingComplaintAmount()).isEqualTo(1);
        assertThat(result.getAnalysedComplaintAmount()).isEqualTo(1);
    }

}