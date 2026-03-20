package com.gamesheets.gamesheets.shared.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class GoogleCloudStorageService {
        private final Path pathKeyGCP = Paths.get("gcp-secret.json");

        @Autowired
        public GoogleCloudStorageService() {
            Storage storage = StorageOptions.newBuilder()
                    .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(pathKeyGCP.toString())))
                    .build()
                    .getService();
        }

        public String uploadFile(MultipartFile file) {
            try {
                Path destinationFile;

                destinationFile = location.resolve(Paths.get(UUID.randomUUID().toString()));
                file.transferTo(destinationFile);

                return destinationFile.toString();
            } catch (IOException e) {
                throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
            }
        }
    }
}
