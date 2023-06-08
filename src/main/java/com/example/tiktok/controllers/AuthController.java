package com.example.tiktok.controllers;

import javax.validation.Valid;

import com.example.tiktok.entities.CustomUserDetails;
import com.example.tiktok.exceptions.NotFoundException;
import com.example.tiktok.models.requests.AuthRequest;
import com.example.tiktok.models.responses.AuthResponse;
import com.example.tiktok.models.responses.ResponseHandler;
import com.example.tiktok.models.responses.UserInformationResponse;
import com.example.tiktok.utils.JwtTokenUtil;
import com.example.tiktok.utils.LanguageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtTokenUtil jwtUtil;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        if (authentication == null) throw new BadCredentialsException(LanguageUtils.getMessage("message.error"));

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        String accessToken = jwtUtil.generateAccessToken(user.getUser());
        AuthResponse response = new AuthResponse(user.getUser().getEmail(), accessToken);

        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, LanguageUtils.getMessage("message.successfully"), response);

    }

    @GetMapping("/auth/getMe")
    public ResponseEntity<?> getMe(Authentication authentication) {
        if (authentication == null) throw new NotFoundException(LanguageUtils.getMessage("message.error"));
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        UserInformationResponse response = new UserInformationResponse();
        response.loadFromEntity(user.getUser());
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, LanguageUtils.getMessage("message.successfully"), response);
    }
}
