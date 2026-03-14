package com.gamesheets.gamesheets.fileprocess;

import com.gamesheets.gamesheets.fileprocess.model.FileProcess;
import com.gamesheets.gamesheets.fileprocess.model.FileProcessStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileProcessServiceTest {
    @Mock
    private FileProcessRepository fileProcessRepository;

    @InjectMocks
    private FileProcessService fileProcessService;

    MockMultipartFile mockMultipartFile = new MockMultipartFile(
            "file",
            "test.csv",
            "text/csv",
            "test,1,bla".getBytes()
    );

    UUID id = UUID.randomUUID();
    FileProcess fileProcess = new FileProcess(
            id,
            FileProcessStatus.QUEUED,
            "testeblabla",
            LocalDateTime.of(2024, 1, 10, 12, 30, 0),
            LocalDateTime.of(2024, 1, 10, 12, 45, 0)
    );

    @Test
    void shouldGetRightFileProcessById() {}

    @Test
    void shouldReturnRightFileProcessWhenCreate() {
       when(fileProcessRepository.save(any())).thenReturn(fileProcess);

       FileProcess result = fileProcessService.createFileProcess(mockMultipartFile);

       assertNotNull(result);
       assertEquals(result, fileProcess);
    }
}
