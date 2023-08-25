package com.example.tiktok.controllers;

import com.example.tiktok.models.responses.ResponseHandler;
import com.example.tiktok.services.FollowersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/followers")
public class FollowersController {

    @Autowired
    private FollowersService followersService;

    @PostMapping("/follow/{toUserId}")
    public ResponseEntity<?> follow(@PathVariable(name = "toUserId") Long toUserId) {
        followersService.follow(toUserId);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "message.successfully", null);
    }

    @GetMapping("/get-follower/{userId}")
    public ResponseEntity<?> getFollowers(@PathVariable(name = "userId") Long userId) {
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "message.successfully", followersService.getFollowers(userId));
    }

    @GetMapping("/get-following/{userId}")
    public ResponseEntity<?> getFollowings(@PathVariable(name = "userId") Long userId) {
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "message.successfully", followersService.getFollowings(userId));
    }

}
