package com.gamesheets.gamesheets.fileprocess;

import com.gamesheets.gamesheets.fileprocess.model.FileProcess;
import com.gamesheets.gamesheets.fileprocess.model.FileProcessStatus;
import com.gamesheets.gamesheets.shared.storage.StorageService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileProcessServiceTest {

    @Mock
    private FileProcessRepository fileProcessRepository;
    @Mock
    private StorageService storageService;
    @InjectMocks
    private FileProcessService fileProcessService;

    private FileProcess randomFileProcess() {
        return new FileProcess(
                UUID.randomUUID(),
                FileProcessStatus.QUEUED,
                "testeblabla",
                LocalDateTime.now(),
                null
        );
    }

    @Test
    void shouldGetFileProcessByIdCorrectly() {
        FileProcess fileProcess = randomFileProcess();
        when(fileProcessRepository.findById(fileProcess.getId())).thenReturn(Optional.of(fileProcess));

        FileProcess result = fileProcessService.getFileProcessById(fileProcess.getId());

        assertNotNull(result);
        assertEquals(result, fileProcess);
    }

    @Test
    void shouldReturnEntityNotFoundException() {
        FileProcess fileProcess = randomFileProcess();
        when(fileProcessRepository.findById(fileProcess.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> fileProcessService.getFileProcessById(fileProcess.getId()));
    }

    @Test
    void shouldCreateFileProcessCorrectly() {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "test.csv",
                "text/csv",
                "test,1,bla".getBytes()
        );
        when(fileProcessRepository.save(any())).thenReturn(randomFileProcess());
        FileProcess result = fileProcessService.createFileProcess(mockMultipartFile);

        assertNotNull(result);
        assertNotNull(result.getFileUrl());
    }

}
