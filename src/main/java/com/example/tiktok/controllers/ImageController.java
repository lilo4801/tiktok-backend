package com.example.tiktok.controllers;

import com.example.tiktok.common.ImageFolder;
import com.example.tiktok.entities.Image;
import com.example.tiktok.exceptions.NotFoundException;
import com.example.tiktok.models.requests.Image.GetImageRequest;
import com.example.tiktok.repositories.ImageRepository;
import com.example.tiktok.utils.FileDownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class ImageController {
    @Autowired
    private FileDownloadUtil fileDownloadUtil;

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("/user/show/image")
    public ResponseEntity<?> getImage(@RequestBody GetImageRequest request) throws IOException {

        Image image = imageRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("message.not.found"));

        MediaType contentType = null;

        if (image.getType().contains("jpeg")) {
            contentType = MediaType.IMAGE_JPEG;
        } else if (image.getType().contains("png")) {
            contentType = MediaType.IMAGE_PNG;
        } else {
            contentType = MediaType.ALL;
        }

        Resource resource = fileDownloadUtil.getFileAsResource(image.getFileCode(), image.getPath());

        return ResponseEntity.ok()
                .contentType(contentType)
                .body(resource);

    }

    @GetMapping("/user/downloadFile/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) throws IOException {


        Resource resource = fileDownloadUtil.getFileAsResource(fileCode, ImageFolder.USER_FOLDER);

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);

    }
}
