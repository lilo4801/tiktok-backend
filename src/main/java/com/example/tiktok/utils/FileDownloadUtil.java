package com.example.tiktok.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.example.tiktok.controllers.DemoController;
import com.example.tiktok.exceptions.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public class FileDownloadUtil {
    private Path foundFile;

    private static final Logger LOGGER = LogManager.getLogger(FileDownloadUtil.class);


    public Resource getFileAsResource(String fileCode, String folder) throws IOException {
        Path dirPath = Paths.get(folder);

        Files.list(dirPath).forEach(file -> {
            if (file.getFileName().toString().startsWith(fileCode)) {
                foundFile = file;
                return;
            }
        });

        if (foundFile == null) {
            LOGGER.error(LanguageUtils.getMessage("message.not-found"));
            throw new NotFoundException(LanguageUtils.getMessage("message.not-found"));
        }

        return new UrlResource(foundFile.toUri());

    }
}