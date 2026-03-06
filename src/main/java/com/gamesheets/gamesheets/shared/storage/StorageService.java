package com.gamesheets.gamesheets.shared.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface StorageService {
    String uploadFile (MultipartFile file, UUID fileId);
    String getFileLink(String fileId);
}
