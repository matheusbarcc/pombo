package com.pruu.pombo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportedPublicationDTO {
    private String publicationId;
    private String publicationContent;
    private String userId;
    private String userName;
    private String userEmail;
    private String userProfilePictureUrl;
    private Integer complaintAmount;
    private Integer pendingComplaintAmount;
    private Integer acceptedComplaintAmount;
    private Integer rejectedComplaintAmount;
}
