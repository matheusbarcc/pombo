package com.pruu.pombo.model.dto;

import com.pruu.pombo.model.enums.ComplaintStatus;
import com.pruu.pombo.model.enums.Reason;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ComplaintDTO {
    private String complaintId;
    private String userId;
    private String userName;
    private String userEmail;
    private String userProfilePictureUrl;
    private Reason reason;
    private ComplaintStatus status;
    private LocalDateTime createdAt;
}
