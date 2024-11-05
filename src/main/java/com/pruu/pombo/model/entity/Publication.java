package com.pruu.pombo.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pruu.pombo.model.dto.PublicationDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    @Size(max = 350, message = "O conteúdo do Pruu deve conter no máximo 300 caracteres.")
    private String content;

    @ManyToMany
    @JoinTable(name = "publication_like", joinColumns = @JoinColumn(name = "publication_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> likes;

    @OneToMany(mappedBy = "publication")
    @JsonBackReference(value = "publication-complaints")
    private List<Complaint> complaints;

    @OneToOne(mappedBy = "publication")
    @JsonBackReference(value = "publication-attachment")
    private Attachment attachment;

    private boolean blocked = false;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public static PublicationDTO toDTO(Publication p, String publicationAttachmentUrl, String profilePictureUrl,
                                       Integer likeAmount, Integer complaintAmount) {
        if(p.isBlocked()) {
            p.setContent("Bloqueado pelo administrador");
        }

        return new PublicationDTO(
                p.getId(),
                p.getContent(),
                publicationAttachmentUrl,
                p.getUser().getId(),
                p.getUser().getName(),
                p.getUser().getEmail(),
                profilePictureUrl,
                likeAmount,
                complaintAmount,
                p.getCreatedAt()
        );
    }
}
