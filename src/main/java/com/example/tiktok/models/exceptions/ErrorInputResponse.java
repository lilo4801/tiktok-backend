package com.example.tiktok.models.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInputResponse {
    private Integer status;
    private String message;
    private Map<String,String> details;
}
