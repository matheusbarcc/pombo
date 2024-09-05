package com.pruu.pombo.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @ManyToMany(mappedBy = "likes")
    @JsonManagedReference
    Set<Pruu> likedPruus;

    @NotBlank
    private String name;

    @NotBlank
    @Email(message = "O email deve ser válido.")
    @Column(unique = true)
    private String email;

    @NotBlank
    @CPF
    @Column(unique = true)
    private String cpf;

    @OneToMany(mappedBy = "user")
    private Set<Pruu> pruus;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER; // Seta o valor default de role como USER quando nao é informado explicitamente

    @CreationTimestamp
    private LocalDate createdAt;
}
