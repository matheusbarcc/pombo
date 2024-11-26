package com.pruu.pombo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PublicationDTO {
    private String publicationId;
    private String publicationContent;
    private String publicationAttachmentUrl;
    private String userId;
    private String userName;
    private String userEmail;
    private String userProfilePictureUrl;
    private List<String> likesUserIds;
    private Integer likeAmount;
    private Integer complaintAmount;
    private LocalDateTime createdAt;
}
