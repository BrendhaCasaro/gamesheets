package com.gamesheets.gamesheets.fileprocess;

import com.gamesheets.gamesheets.fileprocess.model.FileProcess;
import com.gamesheets.gamesheets.shared.storage.StorageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileProcessService {
    final private FileProcessRepository fileProcessRepository;
    final private StorageService storageService;

    public FileProcess getFileProcessById(UUID id) {
        return fileProcessRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FileProcess with id " + id + " not found!"));
    }

    public FileProcess createFileProcess(MultipartFile file) throws IOException {
        FileProcess fileProcess = new FileProcess();
        validateFile(file);
        fileProcessRepository.save(fileProcess);

        storageService.uploadFile(file, fileProcess.getId());

        return fileProcess;
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