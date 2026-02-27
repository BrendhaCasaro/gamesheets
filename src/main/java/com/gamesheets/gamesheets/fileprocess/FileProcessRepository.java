package com.gamesheets.gamesheets.fileprocess;

import com.gamesheets.gamesheets.fileprocess.model.FileProcess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileProcessRepository extends JpaRepository<FileProcess, UUID> {
}
