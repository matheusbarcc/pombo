package com.pruu.pombo.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    @Size(max = 300, message = "O conteúdo do Pruu deve conter no máximo 300 caracteres.")
    private String content;

    @ManyToMany
    @JsonBackReference
    @JoinTable(name = "pruu_like", joinColumns = @JoinColumn(name = "pruu_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    @EqualsAndHashCode.Exclude
    private Set<User> likes;

    @CreationTimestamp
    private LocalDate createdAt;
}
