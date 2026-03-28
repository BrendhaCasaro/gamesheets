package com.gamesheets.gamesheets.shared.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class LocalStorageService implements StorageService {
    private final Path location = Paths.get("tmp");

    @Autowired
    public LocalStorageService() {
        try {
            if (Files.notExists(location)) {
                Files.createDirectories(location);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to create the directory", e);
        }
    }

    public String uploadFile(MultipartFile file) {
        try {
            Path destinationFile = location.resolve(Paths.get(UUID.randomUUID().toString()));
            file.transferTo(destinationFile);

            return destinationFile.toString();
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }
}
