package com.gamesheets.gamesheets.fileprocess;

import com.gamesheets.gamesheets.fileprocess.model.FileProcess;
import com.gamesheets.gamesheets.shared.storage.StorageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileProcessService {
    private final FileProcessRepository fileProcessRepository;
    private final StorageService storageService;

    public FileProcess getFileProcessById(UUID id) {
        return fileProcessRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FileProcess with id " + id + " not found!"));
    }

    public FileProcess createFileProcess(MultipartFile file) {
        FileProcess fileProcess = new FileProcess();
        validateFile(file);

        String url = storageService.uploadFile(file);
        fileProcess.setFileUrl(url);

        return fileProcessRepository.save(fileProcess);
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new MultipartException("File is empty!");
        }

        if (!Objects.equals(file.getContentType(), "text/csv")) {
            throw new MultipartException("File content type is not csv!");
        }
    }
}