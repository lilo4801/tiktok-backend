package com.example.tiktok.controllers;

import javax.validation.Valid;

import com.example.tiktok.entities.CustomUserDetails;
import com.example.tiktok.entities.User;
import com.example.tiktok.models.exceptions.NotFoundException;
import com.example.tiktok.models.requests.AuthRequest;
import com.example.tiktok.models.responses.AuthResponse;
import com.example.tiktok.models.responses.UserInformationResponse;
import com.example.tiktok.utils.JwtTokenUtil;
import com.example.tiktok.utils.LanguageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

        return ResponseEntity.ok().body(response);

    }

    @GetMapping("/auth/getMe")
    public ResponseEntity<?> getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) throw new NotFoundException(LanguageUtils.getMessage("message.error"));
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        UserInformationResponse response = new UserInformationResponse(user.getUser().getEmail(), user.getUser().getRoles());
        return ResponseEntity.ok().body(response);
    }
}
