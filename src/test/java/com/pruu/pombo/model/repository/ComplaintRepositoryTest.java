package com.pruu.pombo.model.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.pruu.pombo.factories.PublicationFactory;
import com.pruu.pombo.factories.UserFactory;
import com.pruu.pombo.model.entity.Complaint;
import com.pruu.pombo.model.entity.Publication;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.enums.Reason;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.TransactionSystemException;

@SpringBootTest
@ActiveProfiles("test")
public class ComplaintRepositoryTest {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user = userRepository.save(UserFactory.createUser());
        publicationRepository.save(PublicationFactory.createPublication(user));
    }

    @Test
    @DisplayName("Should not be able to insert a complaint without reason")
    public void testInsert$complaintWithoutReason() {
        User user = userRepository.findAll().get(0);
        Publication publication = publicationRepository.findAll().get(0);

        Complaint complaint = new Complaint();
        complaint.setUser(user);
        complaint.setPublication(publication);
        assertThatThrownBy(() -> complaintRepository.save(complaint)).isInstanceOf(TransactionSystemException.class);
    }
}
