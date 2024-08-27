package com.pruu.pombo.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
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

    @NotBlank
    @Max(value = 120, message = "O nome deve ter no máximo 120 caracteres.")
    private String name;

    @NotBlank
    @Email(message = "O email deve ser válido.")
    private String email;

    @NotBlank
    @CPF
    private String cpf;

    @OneToMany(mappedBy = "user")
    private Set<Pruu> pruus;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private LocalDate createdAt;
}
