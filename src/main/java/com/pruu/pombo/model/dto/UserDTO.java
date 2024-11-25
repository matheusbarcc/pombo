package com.pruu.pombo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserDTO {
    String userId;
    String name;
    String email;
    String profilePicture;
    LocalDateTime createdAt;
}
