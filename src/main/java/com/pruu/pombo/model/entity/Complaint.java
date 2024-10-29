package com.pruu.pombo.model.entity;

import com.pruu.pombo.model.dto.ComplaintDTO;
import com.pruu.pombo.model.enums.ComplaintStatus;
import com.pruu.pombo.model.enums.Reason;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Data
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @ManyToOne
    @JoinColumn(name = "publication_id")
    private Publication publication;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Escolha o motivo da denuncia.")
    private Reason reason;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ComplaintStatus status = ComplaintStatus.PENDING;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public static ComplaintDTO toDTO(String publicationId, Integer complaintAmount, Integer pendingComplaintAmount, Integer acceptedComplaintAmount, Integer rejectedComplaintAmount) {
        return new ComplaintDTO(
                publicationId,
                complaintAmount,
                pendingComplaintAmount,
                acceptedComplaintAmount,
                rejectedComplaintAmount
        );
    }
}
