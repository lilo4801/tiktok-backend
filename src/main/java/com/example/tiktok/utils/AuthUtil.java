package com.example.tiktok.utils;

import com.example.tiktok.entities.CustomUserDetails;
import com.example.tiktok.exceptions.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class AuthUtil {
    public static Long getLoggedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            return customUserDetails.getUser().getId();
        } catch (ClassCastException e) {
            throw new NotFoundException("message.not.found");
        }
    }
}
