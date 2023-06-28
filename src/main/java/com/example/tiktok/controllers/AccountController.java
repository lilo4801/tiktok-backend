package com.example.tiktok.controllers;


import com.example.tiktok.common.ImageFolder;
import com.example.tiktok.models.requests.users.UpdateProfileRequest;
import com.example.tiktok.models.requests.users.UploadImageRequest;
import com.example.tiktok.models.responses.FileUploadResponse;
import com.example.tiktok.models.responses.ResponseHandler;
import com.example.tiktok.services.UserImageService;
import com.example.tiktok.services.UserService;
import com.example.tiktok.utils.FileDownloadUtil;
import com.example.tiktok.utils.LanguageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;


import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LogManager.getLogger(AccountController.class);


    @PostMapping("/user/update-profile")
    public ResponseEntity<?> updateProfile(@RequestParam("file") MultipartFile multipartFile,
                                           @RequestParam("username") String username,
                                           @RequestParam("nickname") String nickname,
                                           @RequestParam("bio") String bio

    ) throws IOException {
        LOGGER.info("Excuting controller");
        userService.updateProfile(UpdateProfileRequest.builder()
                .username(username)
                .nickname(nickname)
                .bio(bio).build(), multipartFile);
        LOGGER.info("Done executing controller");
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, LanguageUtils.getMessage("message.successfully"), null);

    }

    @GetMapping("/user/downloadFile/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) throws IOException {
        LOGGER.info("Excuting controller");

        FileDownloadUtil downloadUtil = new FileDownloadUtil();

        Resource resource = downloadUtil.getFileAsResource(fileCode, ImageFolder.USER_FOLDER);

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        LOGGER.info("Done executing controller");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);

    }

}
