package com.gamesheets.gamesheets.fileprocess;

import com.gamesheets.gamesheets.fileprocess.model.FileProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/process")
@RequiredArgsConstructor
public class FileProcessController {
    private final FileProcessService fileProcessService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getFileProcessById(@PathVariable UUID id) {
        FileProcess fileProcess = fileProcessService.getFileProcessById(id);
        return ResponseEntity.ok(fileProcess);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadFileById(@PathVariable UUID id) {
        Resource gamesCSV = fileProcessService.getCSVByFileProcessId(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename:\"" + gamesCSV.getFilename() + "\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(gamesCSV);
    }

    @PostMapping
    public ResponseEntity<?> createFileProcess(@RequestParam MultipartFile file) {
        return ResponseEntity.ok(fileProcessService.createFileProcess(file));
    }

}
