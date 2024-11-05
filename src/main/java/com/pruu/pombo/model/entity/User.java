package com.pruu.pombo.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pruu.pombo.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @NotBlank(message = "O nome não deve estar em branco.")
    @Size(max = 200, message = "O nome deve conter no máximo 200 caracteres.")
    private String name;

    @NotBlank(message = "O email não deve estar em branco.")
    @Email(message = "O email deve ser válido.")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "O CPF não deve estar em branco.")
    @CPF(message = "O CPF deve ser válido.")
    @Column(unique = true)
    private String cpf;

    @NotBlank(message = "A senha não deve estar em branco.")
    @Size(max = 500)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @OneToMany(mappedBy = "user")
    @JsonBackReference(value = "user-publications")
    private List<Publication> publications;

    @OneToMany(mappedBy = "user")
    @JsonBackReference(value = "user-complaints")
    private List<Complaint> complaints;

    @OneToOne(mappedBy = "user")
    @JsonBackReference
    private Attachment profilePicture;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        authorities.add(new SimpleGrantedAuthority(role.toString()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
