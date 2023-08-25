package com.example.tiktok.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String content;
    
}
