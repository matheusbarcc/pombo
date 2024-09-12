package com.pruu.pombo.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @ManyToOne
    @JoinColumn(name = "pruu_id")
    private Pruu pruu;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Size(max = 300, message = "A denuncia deve conter no máximo 300 caracteres.")
    private String reason;
}
