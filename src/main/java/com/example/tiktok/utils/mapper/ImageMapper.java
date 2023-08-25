package com.example.tiktok.utils.mapper;

import com.example.tiktok.entities.Image;
import com.example.tiktok.models.dto.ImageDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper extends IBaseMapper<Image, ImageDTO> {
}
