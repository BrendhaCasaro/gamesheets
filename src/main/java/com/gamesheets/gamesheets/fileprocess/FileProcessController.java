package com.gamesheets.gamesheets.fileprocess;

import com.gamesheets.gamesheets.fileprocess.model.FileProcess;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/process")
@RequiredArgsConstructor
public class FileProcessController {
    private FileProcessService fileProcessService;

   @GetMapping
   public ResponseEntity<?> getFileProcessById(@RequestParam UUID id) {
       try {
           FileProcess fileProcess = fileProcessService.getFileProcessById(id);
           return ResponseEntity.ok(fileProcess);
       } catch (EntityNotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
   }

   @PostMapping
   public ResponseEntity<?> createFileProcess() {
       return ResponseEntity.ok(fileProcessService.createFileProcess());
   }

}
