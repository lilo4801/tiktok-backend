package com.example.tiktok.models.requests.users;

import com.example.tiktok.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @NotNull
    @Email
    @Length(min = 5, max = 50)
    private String email;

    @NotNull
    @Length(min = 3, max = 10)
    private String password;

    @NotNull
    @Length(min = 5, max = 50)
    private String nickname;
    private String bio;

    public User loadToEntity() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .bio(this.bio)
                .nickname(this.nickname)
                .build();
    }
}
