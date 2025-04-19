package com.server.side.util;

import com.server.side.exception.FileStorageException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    public static String saveFile(MultipartFile file){
        String uploadDir = "../uploads/images/";
        try {
            Path path = Paths.get(uploadDir + file.getOriginalFilename());
            Files.copy(file.getInputStream(), path);
            return path.toString();
        } catch(IOException e) {
            throw new FileStorageException("{file.storage.failed}" + file.getOriginalFilename(), e);

        }

    }
}
