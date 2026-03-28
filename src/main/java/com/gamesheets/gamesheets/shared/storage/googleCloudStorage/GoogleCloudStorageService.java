/*
package com.gamesheets.gamesheets.shared.storage.googleCloudStorage;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import com.google.cloud.storage.Storage;

import static jdk.xml.internal.SecuritySupport.getResourceAsStream;

public class GoogleCloudStorageService {
        private final Path pathKeyGCP = Paths.get("gcp-secret.json");

        @Autowired
        public GoogleCloudStorageService() {
            try {
                Storage storage = StorageOptions.newBuilder()
                        .setCredentials(ServiceAccountCredentials.fromStream(getResourceAsStream("gcp-secret.json")))
                        .build()
                        .getService();
            } catch (IOException e) {
                throw new StorageException("Failed to get credentials of GCS", e);
            }
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
*/