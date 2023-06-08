package com.example.tiktok.models.responses;

import com.example.tiktok.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInformationResponse {
    private String email;
    private Set<Role> roles;
    // more
}
