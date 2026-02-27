package com.gamesheets.gamesheets.fileprocess;

import com.gamesheets.gamesheets.fileprocess.model.FileProcess;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileProcessService {
    final private FileProcessRepository fileProcessRepository;

    public FileProcess getFileProcessById(UUID id) {
        return fileProcessRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FileProcess with id " + id + " not found!"));
    }

    public FileProcess createFileProcess() {
        FileProcess fileProcess = new FileProcess();
        return fileProcessRepository.save(fileProcess);
    }
}
