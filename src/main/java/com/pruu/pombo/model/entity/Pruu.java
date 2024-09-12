package com.pruu.pombo.model.entity;

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
public class Pruu {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    @Size(min = 1, max = 300, message = "O conteúdo do Pruu deve conter no máximo 300 caracteres.")
    private String content;

    @ManyToMany
    @JoinTable(name = "pruu_like", joinColumns = @JoinColumn(name = "pruu_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> likes;

    private boolean blocked;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
