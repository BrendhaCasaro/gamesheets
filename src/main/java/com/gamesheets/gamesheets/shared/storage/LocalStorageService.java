package com.gamesheets.gamesheets.shared.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class LocalStorageService implements StorageService {

    public String uploadFile(MultipartFile file, UUID fileId) throws IOException {
        Path location = Paths.get("tmp");

        if (!Files.exists(location)) {
            Files.createDirectories(location);
        }

        Path destinationFile;

        try {
            destinationFile = location.resolve(
                    Paths.get(fileId.toString()));

            file.transferTo(destinationFile);
        } catch (IOException e) {
            throw new IOException("Failed to store file " + file.getOriginalFilename(), e);
        }

        return destinationFile.toString();
    }

    public String getFileLink(String fileId) {
        return "";
    }
}
