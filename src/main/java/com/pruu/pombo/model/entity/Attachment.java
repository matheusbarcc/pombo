package com.pruu.pombo.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @OneToOne
    @JoinColumn(name = "publication_id")
    private Publication publication;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private String url;
}
