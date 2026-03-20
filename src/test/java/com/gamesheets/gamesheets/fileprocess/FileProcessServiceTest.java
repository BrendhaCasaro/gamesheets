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
import org.springframework.web.multipart.MultipartException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> fileProcessService.getFileProcessById(fileProcess.getId())
        );
        assertEquals("FileProcess with id " + fileProcess.getId() + " not found!", exception.getMessage());
    }

    @Test
    void shouldCreateFileProcessCorrectly() {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "test.csv",
                "text/csv",
                "test,1,bla".getBytes()
        );
        when(storageService.uploadFile(mockMultipartFile)).thenReturn("https://example.com/test.csv");
        when(fileProcessRepository.save(any())).thenReturn(randomFileProcess());
        FileProcess result = fileProcessService.createFileProcess(mockMultipartFile);

        assertNotNull(result);
        assertNotNull(result.getFileUrl());
        verify(storageService).uploadFile(mockMultipartFile);
        verify(fileProcessRepository).save(any(FileProcess.class));
    }

    @Test
    void shouldThrowMultipartExceptionWhenFileIsEmpty() {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "test.csv",
                "text/csv",
                new byte[0]
        );

        MultipartException exception = assertThrows(
                MultipartException.class,
                () -> fileProcessService.createFileProcess(mockMultipartFile)
        );
        assertEquals("File is empty!", exception.getMessage());
    }

    @Test
    void shouldThrowMultipartExceptionWhenFileContentTypeIsNotCsv() {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "test".getBytes()
        );

        MultipartException exception = assertThrows(
                MultipartException.class,
                () -> fileProcessService.createFileProcess(mockMultipartFile)
        );
        assertEquals("File content type is not csv!", exception.getMessage());
    }

}
