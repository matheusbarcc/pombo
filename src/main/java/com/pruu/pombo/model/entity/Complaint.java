package com.pruu.pombo.model.entity;

import com.pruu.pombo.model.dto.ComplaintDTO;
import com.pruu.pombo.model.dto.ReportedPublicationDTO;
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

    public static ReportedPublicationDTO toReportedPublicationDTO(Publication p,
                                                                  String profilePictureUrl,
                                                                  Integer complaintAmount,
                                                                  Integer pendingComplaintAmount,
                                                                  Integer acceptedComplaintAmount,
                                                                  Integer rejectedComplaintAmount) {
        return new ReportedPublicationDTO(
                p.getId(),
                p.getContent(),
                p.getUser().getId(),
                p.getUser().getName(),
                p.getUser().getEmail(),
                profilePictureUrl,
                complaintAmount,
                pendingComplaintAmount,
                acceptedComplaintAmount,
                rejectedComplaintAmount,
                p.getCreatedAt()
        );
    }

    public static ComplaintDTO toComplaintDTO(Complaint c, String userProfilePictureUrl) {
        return new ComplaintDTO(
                c.getId(),
                c.getUser().getId(),
                c.getUser().getName(),
                c.getUser().getEmail(),
                userProfilePictureUrl,
                c.getReason(),
                c.getStatus(),
                c.getCreatedAt()
        );
    }
}
