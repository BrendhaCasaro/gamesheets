package com.gamesheets.gamesheets.fileprocess;

import com.gamesheets.gamesheets.fileprocess.model.FileProcess;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/process")
@RequiredArgsConstructor
public class FileProcessController {
    private final FileProcessService fileProcessService;

    @GetMapping
   public ResponseEntity<?> getFileProcessById(@RequestParam UUID id) {
       FileProcess fileProcess = fileProcessService.getFileProcessById(id);
       return ResponseEntity.ok(fileProcess);
   }

   @PostMapping
   public ResponseEntity<?> createFileProcess(@RequestParam MultipartFile file) {
       return ResponseEntity.ok(fileProcessService.createFileProcess(file));
   }

}
