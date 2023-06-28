package com.example.tiktok.models.requests.users;

import com.example.tiktok.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProfileRequest {

    @NotNull
    @Length(min = 5, max = 50)
    private String username;
    @NotNull
    @Length(min = 5, max = 50)
    private String nickname;
    @Length(max = 80)
    private String bio;


    public User loadToEntity() {
        return User.builder()
                .bio(this.bio)
                .nickname(this.nickname)
                .username(this.username)
                .build();
    }

}
