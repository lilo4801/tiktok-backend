package com.example.tiktok.models.responses;

import com.example.tiktok.entities.Post;
import com.example.tiktok.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TestResponse {
    private List<User> users;
    private List<Post> posts;
}
