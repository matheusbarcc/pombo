package com.pruu.pombo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    String name;
    String email;
    String profilePicture;
}
