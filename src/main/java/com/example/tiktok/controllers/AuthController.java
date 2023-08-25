package com.example.tiktok.controllers;

import com.example.tiktok.entities.CustomUserDetails;
import com.example.tiktok.exceptions.LoginExeception;
import com.example.tiktok.models.requests.AuthRequest;
import com.example.tiktok.models.requests.users.SignUpRequest;
import com.example.tiktok.models.responses.AuthResponse;
import com.example.tiktok.models.responses.ResponseHandler;
import com.example.tiktok.models.responses.UserInformationResponse;
import com.example.tiktok.services.UserService;
import com.example.tiktok.utils.JwtTokenUtil;
import com.example.tiktok.utils.LanguageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtTokenUtil jwtUtil;

    @Autowired
    UserService userService;


    @PostMapping("/auth/login")
    public ResponseEntity<?> login(
            @RequestBody
            @Valid AuthRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        if (authentication == null) throw new BadCredentialsException("message.error");

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        String accessToken = jwtUtil.generateAccessToken(user.getUser());
        AuthResponse response = new AuthResponse(user.getUser().getEmail(), accessToken);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "message.successfully", response);

    }


    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody @Valid SignUpRequest request) {
        userService.register(request);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "message.successfully", null);

    }

    @GetMapping("/auth/getMe")
    public ResponseEntity<?> getMe() {
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "message.successfully", userService.getUserInformation());
    }
}
