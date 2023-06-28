package com.example.tiktok.services;

import com.example.tiktok.common.ImageFolder;
import com.example.tiktok.controllers.AccountController;
import com.example.tiktok.entities.User;
import com.example.tiktok.entities.UserImage;
import com.example.tiktok.exceptions.CreateFailureException;
import com.example.tiktok.models.requests.users.UploadImageRequest;
import com.example.tiktok.models.responses.FileUploadResponse;
import com.example.tiktok.repositories.UserImageRepository;
import com.example.tiktok.repositories.UserRepository;
import com.example.tiktok.utils.FileUploadUtil;
import com.example.tiktok.utils.LanguageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Objects;

@Service
public class UserImageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserImageRepository userImageRepository;

    private static final Logger LOGGER = LogManager.getLogger(UserImageService.class);

    public FileUploadResponse uploadImage(UploadImageRequest req) throws IOException {
        LOGGER.info("Executing service...");
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(req.getFile().getOriginalFilename()));
        long size = req.getFile().getSize();

        String filecode = FileUploadUtil.saveFile(fileName, req.getFile(), ImageFolder.USER_FOLDER);

        try {
            userImageRepository.save(UserImage.builder()
                    .type(req.getFile().getContentType())
                    .name(fileName)
                    .fileCode(filecode)
                    .build());
        } catch (DataAccessException e) {
            LOGGER.error("Executing error service");
            throw new CreateFailureException(LanguageUtils.getMessage("message.create.failure"));
        }

        LOGGER.info("Completely service");
        return FileUploadResponse.builder()
                .fileName(fileName)
                .size(size)
                .downloadUri("/downloadFile/" + filecode)
                .build();
    }
}
