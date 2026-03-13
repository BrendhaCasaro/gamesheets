package com.gamesheets.gamesheets.fileprocess;

import com.gamesheets.gamesheets.fileprocess.model.FileProcess;
import com.gamesheets.gamesheets.fileprocess.model.FileProcessStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileProcessTest {
    @Mock
    private FileProcessRepository fileProcessRepository;

    @InjectMocks
    private FileProcessService fileProcessService;

    @Test
    void shouldCreateFileProcessWhenIdExists() {
        UUID id = UUID.randomUUID();
        String content = "test,1,bla";
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "test.csv",
                "text/csv",
                content.getBytes()
        );
    }
}
