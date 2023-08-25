package com.example.tiktok.models.responses;

import com.example.tiktok.entities.Role;
import com.example.tiktok.models.dto.ImageDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserProfileResponse {
    private Long id;
    private String username;
    private String nickname;
    private String bio;
    private String email;
    private ImageDTO avatar;
    private Long numberOfFollower;

    private Long numberOfFollowing;

    private boolean isFollowed;

}
