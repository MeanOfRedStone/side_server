package com.server.side.util;

import com.server.side.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileManager {

    @Value("${file.upload-dir}")
    private String uploadDir;
    @Value("${file.relative-dir}")
    private String relativeDir;

    public String saveFile(MultipartFile file){
        try {
            Path path = Paths.get(uploadDir + file.getOriginalFilename());
            Files.copy(file.getInputStream(), path);
            String relativePath = path.toString().replace(uploadDir, relativeDir);
            return relativePath;
        } catch(IOException e) {
            throw new FileStorageException("file.storage.failed", e);
        }
    }
}
