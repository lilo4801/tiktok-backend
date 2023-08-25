package com.example.tiktok.utils.mapper;

import com.example.tiktok.entities.User;
import com.example.tiktok.models.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ImageMapper.class})

public interface UserMapper extends IBaseMapper<User, UserDTO> {
}
