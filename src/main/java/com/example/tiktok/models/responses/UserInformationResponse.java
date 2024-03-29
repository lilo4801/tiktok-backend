package com.example.tiktok.models.responses;

import com.example.tiktok.entities.Role;
import com.example.tiktok.models.dto.ImageDTO;
import com.example.tiktok.models.dto.UserImageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInformationResponse {

    private Long id;
    private String username;

    private String nickname;

    private String bio;
    private String email;

    private Set<Role> roles;
    private ImageDTO avatar;


}
