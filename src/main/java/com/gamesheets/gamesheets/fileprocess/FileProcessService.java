package com.gamesheets.gamesheets.fileprocess;

import com.gamesheets.gamesheets.fileprocess.model.FileProcess;
import com.gamesheets.gamesheets.games.GameService;
import com.gamesheets.gamesheets.games.dto.Game;
import com.gamesheets.gamesheets.shared.storage.StorageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileProcessService {
    private final FileProcessRepository fileProcessRepository;
    private final StorageService storageService;
    private final GameService gameService;

    public FileProcess getFileProcessById(UUID id) {
        return fileProcessRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FileProcess with id " + id + " not found!"));
    }

    public FileProcess createFileProcess(MultipartFile file) {
        FileProcess fileProcess = new FileProcess();
        validateFile(file);
        // add logic of status of file process
        List<String> titles = getTitlesFromCSV(file);
        List<Game> games = gameService.getGamesDetails(titles);
        MultipartFile generatedFile = generateCSVFromGames(games);

        String url = storageService.uploadFile(generatedFile);
        fileProcess.setFileUrl(url);

        return fileProcessRepository.save(fileProcess);
    }

    public Resource getCSVByFileProcessId(UUID fileProcessId) {
        FileProcess fileProcess = getFileProcessById(fileProcessId);

        return new FileSystemResource(fileProcess.getFileUrl());
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new MultipartException("File is empty!");
        }

        if (!Objects.equals(file.getContentType(), "text/csv")) {
            throw new MultipartException("File content type is not csv!");
        }
    }

    private List<String> getTitlesFromCSV(MultipartFile file) {
        List<String> titles = new ArrayList<>();
        String fileContent;

        try {
            fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new MultipartException("Error while reading file!", e);
        }

        String[] lines = fileContent.split("\n");
        for (String line : lines) {
            String[] columns = line.split(",");
            titles.add(columns[0]);
        }

        return titles;
    }

    private MultipartFile generateCSVFromGames(List<Game> games) {
        List<String> csvLines = new ArrayList<>(games.size());

        for (Game game : games) {
            String platforms = String.join(" | ", game.platforms());
            String[] lines = {game.title(), game.backgroundImage(), game.released(), platforms, game.metacritic().toString()};
            csvLines.add(String.join(",", lines));
        }
        String contentFile = String.join("\n", csvLines);
        // lines = batman, link, released, ...
        // "batman, link, ..."
        // "batman,link\nrobin,link\n",

        return new MockMultipartFile(
                "file",
                UUID.randomUUID() + ".csv",
                "text/csv",
                contentFile.getBytes(StandardCharsets.UTF_8)
        );
    }
}