package com.pruu.pombo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComplaintDTO {
    private String publicationId;
    private Integer complaintAmount;
    private Integer pendingComplaintAmount;
    private Integer acceptedComplaintAmount;
    private Integer rejectedComplaintAmount;
}
