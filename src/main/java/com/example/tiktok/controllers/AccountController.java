package com.example.tiktok.controllers;


import com.example.tiktok.models.requests.users.UpdateProfileRequest;
import com.example.tiktok.models.responses.ResponseHandler;
import com.example.tiktok.models.responses.UserProfileResponse;
import com.example.tiktok.services.UserService;
import com.example.tiktok.utils.LanguageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user")
public class AccountController {

    @Autowired
    private UserService userService;


    @PostMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@RequestParam("file") MultipartFile multipartFile,
                                           @RequestParam("username") String username,
                                           @RequestParam("nickname") String nickname,
                                           @RequestParam("bio") String bio

    ) throws IOException {
        userService.updateProfile(UpdateProfileRequest.builder()
                .username(username)
                .nickname(nickname)
                .bio(bio)
                .file(multipartFile)
                .build()
        );
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "message.successfully", null);

    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getProfile(@PathVariable(name = "userId") Long userId) throws IOException {

        UserProfileResponse res = userService.getUserProfile(userId);

        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "message.successfully", res);

    }


}
