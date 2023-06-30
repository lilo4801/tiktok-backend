package com.example.tiktok.controllers;

import javax.validation.Valid;

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
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Api(value = "User APIs")
public class AuthController {
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtTokenUtil jwtUtil;

    @Autowired
    UserService userService;

    @ApiOperation(value = "Login", response = AuthResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Thành công"),
            @ApiResponse(code = 401, message = "Chưa xác thực"),
            @ApiResponse(code = 403, message = "Truy cập bị cấm"),
            @ApiResponse(code = 404, message = "Không tìm thấy")
    })
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(
            @RequestBody
            @ApiParam(value = "auth information request", required = true)
            @Valid AuthRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        if (authentication == null) throw new BadCredentialsException(LanguageUtils.getMessage("message.error"));

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        String accessToken = jwtUtil.generateAccessToken(user.getUser());
        AuthResponse response = new AuthResponse(user.getUser().getEmail(), accessToken);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, LanguageUtils.getMessage("message.successfully"), response);

    }


    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody @Valid SignUpRequest request) {
        userService.register(request);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, LanguageUtils.getMessage("message.successfully"), null);

    }

    @ApiOperation(value = "get current user", response = UserInformationResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Thành công"),
            @ApiResponse(code = 401, message = "Chưa xác thực"),
            @ApiResponse(code = 403, message = "Truy cập bị cấm"),
            @ApiResponse(code = 404, message = "Không tìm thấy")
    })
    @GetMapping("/auth/getMe")
    public ResponseEntity<?> getMe(
            @ApiParam(value = "Đối tượng Authentication", required = true)
            Authentication authentication) {
        if (authentication == null) throw new LoginExeception(LanguageUtils.getMessage("message.error.not.have.token"));
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        UserInformationResponse response = userService.getUserInformation(user.getUser().getId());
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, LanguageUtils.getMessage("message.successfully"), response);
    }
}
