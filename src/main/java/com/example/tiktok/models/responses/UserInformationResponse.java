package com.example.tiktok.models.responses;

import com.example.tiktok.entities.Role;
import com.example.tiktok.entities.User;
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

    public void loadFromEntity(User user) {
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }
}
