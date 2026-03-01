package com.gamesheets.gamesheets.fileprocess;

import com.gamesheets.gamesheets.fileprocess.model.FileProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
   public ResponseEntity<?> createFileProcess() {
       return ResponseEntity.ok(fileProcessService.createFileProcess());
   }

}
