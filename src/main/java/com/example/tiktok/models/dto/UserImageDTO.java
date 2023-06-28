package com.example.tiktok.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserImageDTO {
    Long userImageId;
    String userImageName;
    String userImageFileCode;
}
