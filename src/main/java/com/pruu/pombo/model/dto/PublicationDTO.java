package com.pruu.pombo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublicationDTO {
    private String id;
    private String content;
    private boolean blocked;
    private String userId;
    private String userName;
    private Integer likeAmount;
    private Integer complaintAmount;
}
