package com.example.tiktok.utils.mapper;

public interface IBaseMapper<S, DTO> {
    DTO mapToDTO(S source);

    S mapToEntity(DTO dto);
}