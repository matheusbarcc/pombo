package com.pruu.pombo.model.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
@Data
public class Pruu {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_fk")
    private User user;

    @NotBlank
    @Max(value = 300, message = "O texto deve ter no máximo 300 caracteres.")
    private String content;

    @OneToMany(mappedBy = "pruu")
    private Set<User> likes;

    @CreationTimestamp
    private LocalDate createdAt;
}
