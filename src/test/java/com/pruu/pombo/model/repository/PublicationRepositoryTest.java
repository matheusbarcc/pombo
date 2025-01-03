package com.pruu.pombo.model.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.pruu.pombo.factories.PublicationFactory;
import com.pruu.pombo.factories.UserFactory;
import com.pruu.pombo.model.entity.Publication;
import com.pruu.pombo.model.entity.User;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.TransactionSystemException;

@SpringBootTest
@ActiveProfiles("test")
public class PublicationRepositoryTest {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserRepository userRepository;

    // Commented test because the validation is now being done at service due to content encoding

//    @Test
//    @DisplayName("Should not be able to insert publication with content bigger than 300 characters")
//    public void testInsert$contentMoreThan300Characters() {
//        User user = UserFactory.createUser();
//        userRepository.save(user);
//        Publication publication = PublicationFactory.createPublication(user);
//        String content = "a";
//        publication.setContent(content.repeat(301));
//
//        assertThatThrownBy(() -> publicationRepository.saveAndFlush(publication)).isInstanceOf(ConstraintViolationException.class)
//                .hasMessageContaining("O conteúdo do Pruu deve conter no máximo 300 caracteres");
//    }
}
