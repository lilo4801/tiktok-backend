package com.example.tiktok.utils;

import com.example.tiktok.models.dto.ImageDTO;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import org.apache.commons.lang3.RandomStringUtils;

public class FileUploadUtils {
    public static ImageDTO saveFile(MultipartFile multipartFile, String folder)
            throws IOException {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        Path uploadPath = Paths.get(folder);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileCode = RandomStringUtils.randomAlphanumeric(8);

        InputStream inputStream = multipartFile.getInputStream();
        Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        inputStream.close();

        return ImageDTO.builder()
                .fileCode(fileCode)
                .path(folder)
                .name(fileName)
                .type(multipartFile.getContentType())
                .build();
    }
}
