package com.pruu.pombo.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pruu.pombo.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @OneToMany(mappedBy = "user")
    @JsonBackReference(value = "user-publications")
    private List<Publication> publications;

    @OneToMany(mappedBy = "user")
    @JsonBackReference(value = "user-complaints")
    private List<Complaint> complaints;

    @NotBlank
    @Size(max = 200, message = "O nome deve conter no máximo 200 caracteres.")
    private String name;

    @NotBlank
    @Email(message = "O email deve ser válido.")
    @Column(unique = true)
    private String email;

    @NotBlank
    @CPF(message = "O CPF deve ser válido.")
    @Column(unique = true)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;


    @CreationTimestamp
    private LocalDateTime createdAt;
}
