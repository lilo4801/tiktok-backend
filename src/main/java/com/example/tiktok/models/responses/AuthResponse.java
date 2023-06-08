package com.example.tiktok.models.responses;

import com.example.tiktok.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String email;
    private String accessToken;

    public void loadFromEntity(User user) {
        this.email = user.getEmail();
    }
}